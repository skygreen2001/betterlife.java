package com.zyp.bb;

import com.zyp.bb.domain.Greeting;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;

/**
 * Created by skygreen on 2017/8/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class WebsocketTest {

    //[Connecting to HTML5 Websocket](https://stackoverflow.com/questions/14554273/connecting-to-html5-websocket/14555549#14555549)
    //[failed: Unexpected response code: 200](http://procbits.com/2013/10/09/connecting-to-a-sockjs-server-from-native-html5-websocket)
    @Value("${websocket.url}")
    private String websocketUrl;

    private final Logger logger = LoggerFactory.getLogger(WebsocketTest.class);
    private WebSocketClient webSocketClient;
    private WebSocketStompClient stompClient;
    private ThreadPoolTaskScheduler taskScheduler;
    private CountDownLatch latch;
    private StompSessionHandler sessionHandler;


    @Before
    public void setup() {
        webSocketClient = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(webSocketClient);
//        stompClient.setMessageConverter(new StringMessageConverter());
        MappingJackson2MessageConverter m = new MappingJackson2MessageConverter();
        stompClient.setMessageConverter(m);
        taskScheduler = new ThreadPoolTaskScheduler();
        latch = new CountDownLatch(1);
    }

    public static void main(String[] args){
        WebsocketTest wt=new WebsocketTest();
        wt.setup();
        wt.connectTest();
    }

    @Test
    public void connectTest(){
        this.connect();
    }

    public void connect(){
        //Calls initialize() after the container applied all property values.
        taskScheduler.afterPropertiesSet();
        stompClient.setTaskScheduler(taskScheduler);// for heartbeats

        sessionHandler = new MyStompSessionHandler();
        stompClient.connect(websocketUrl, sessionHandler);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(sessionHandler);
    }

    @Component
    public class MyStompSessionHandler extends StompSessionHandlerAdapter {

        public MyStompSessionHandler() {

        }

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            try {
                Greeting message = new Greeting();
                message.setName("小淘气");
                message.setMessage("小陶");
                session.send("/app/hello", message);
                session.setAutoReceipt(true);

                session.subscribe("/topic/greetings", new StompFrameHandler() {

                    @Override
                    public Type getPayloadType(StompHeaders headers) {
//                        return String.class;
                        return Greeting.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        logger.debug(String.format("Message received: %s", payload));
                    }

                });
            } finally {
                latch.countDown();
            }

        }
    }

}
