package com.zyp.bb.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zyp.bb.message.amqp.Sender;
import com.zyp.bb.service.MsgHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * [STOMP Over WebSocket](http://jmesnil.net/stomp-websocket/doc/)
 * <p>
 * [Stomp Over Websocket文档](https://segmentfault.com/a/1190000006617344)
 * <p>
 * [spring websocket
 * 基于编码的方式手动进行推送](http://www.voidcn.com/blog/yingxiake/article/p-5789769.html)
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    private Map<String, String> sessionUserInfos = new HashMap<>();

    @Autowired
    Sender sender;


    @Autowired
    private MsgHandleService msgService;


    private final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");

        registry.enableSimpleBroker("/queue/", "/topic", "/tick");

        registry.enableSimpleBroker("/")
                .setTaskScheduler(new DefaultManagedTaskScheduler())
                .setHeartbeatValue(new long[]{10000, 10000});
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> arg0) {
        MappingJackson2MessageConverter mc = new MappingJackson2MessageConverter();
        arg0.add(mc);

        StringMessageConverter strConvertor = new StringMessageConverter();
        arg0.add(strConvertor);
        return true;
    }

    //https://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setSendTimeLimit(15 * 1000).setSendBufferSizeLimit(512 * 1024);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/better-websocket").setAllowedOrigins("*").withSockJS();
        // .setClientLibraryUrl("//w/js/sockjs.min.js");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {

            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {

                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                String accessToken;
                if (accessor.getMessageType().toString().equals("HEARTBEAT")) {
                    accessToken = sessionUserInfos.get(accessor.getSessionId());
                    if (!StringUtils.isEmpty(accessToken)) msgService.recordHeartbeat(accessToken);
                    logger.debug("heart beat:" + accessToken);
                } else {
                    if (accessor.getCommand() != null) {
                        switch (accessor.getCommand()) {
                            case STOMP:
                                accessToken = accessor.getNativeHeader("Access-token").get(0);
                                logger.debug("stomp:" + accessToken);
                                break;
                            case CONNECT:
                                accessToken = accessor.getFirstNativeHeader("accessToken");
                                logger.debug("connect:" + accessToken);
                                if (!StringUtils.isEmpty(accessToken)) {
                                    accessToken = accessor.getNativeHeader("accessToken").get(0);
                                    List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
                                    Authentication auth = new UsernamePasswordAuthenticationToken(accessToken, null, grantedAuthorities);
                                    SecurityContextHolder.getContext().setAuthentication(auth);
                                    accessor.setUser(auth);
                                    sessionUserInfos.put(accessor.getSessionId(), accessToken);
                                }
                                break;
                            case DISCONNECT:
                                accessToken = sessionUserInfos.get(accessor.getSessionId());
                                if (!StringUtils.isEmpty(accessToken)) {
                                    logger.debug("disconnect:" + accessToken);
                                    msgService.handleLeave(accessToken);
                                }
                                break;
                            case SUBSCRIBE:
                                accessToken = sessionUserInfos.get(accessor.getSessionId());
                                logger.debug("subscribe:" + accessToken + ";destination" + accessor.getDestination());
                                break;
                            case SEND:
                                accessToken = sessionUserInfos.get(accessor.getSessionId());
                                if (accessor.getDestination().equals("/app/tick")) {
                                    if (!StringUtils.isEmpty(accessToken)) {
                                        msgService.recordHeartbeat(accessToken);
                                        msgService.handleOfflineMsgs(accessToken);
                                    }
                                    logger.debug("/app/tick");
                                }
                            case ACK:
                                accessToken = accessor.getNativeHeader("Access-token").get(0);
                                logger.debug("ACK:" + accessToken);
                                break;
                            case NACK:
                                accessToken = accessor.getNativeHeader("Access-token").get(0);
                                logger.debug("NACK:" + accessToken);
                                break;
                            case CONNECTED:
                                accessToken = sessionUserInfos.get(accessor.getSessionId());
                                logger.debug("connected:" + accessToken);
                                break;
                            case RECEIPT:
                                accessToken = accessor.getNativeHeader("Access-token").get(0);
                                logger.debug("receipt:" + accessToken);
                                break;
                            case MESSAGE:
                                accessToken = accessor.getNativeHeader("Access-token").get(0);
                                logger.debug("message:" + accessToken);
                                break;
                            case ERROR:
                                accessToken = accessor.getNativeHeader("Access-token").get(0);
                                logger.debug("error:" + accessToken);
                                break;
                            default:
                                break;
                        }
                    }
                }

//            MessageHeaders mhds = accessor.getMessageHeaders();
//            for (String key : mhds.keySet()
//                    ) {
//                String value = mhds.get(key).toString();
//                logger.debug("key:" + key + ";value:" + value);
//            }
                return message;
            }
        });
    }


}
