package com.zyp.bb.rest;

import java.util.List;
import java.util.Map;

import com.zyp.bb.service.MsgHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyp.bb.domain.Greeting;
import com.zyp.bb.service.CustomerService;

@RestController
public class GreetingResource {

    private final Logger logger = LoggerFactory.getLogger(GreetingResource.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MsgHandleService msgHandleService;

    @RequestMapping("/greet")
    public Greeting greet() {
        return new Greeting("Hello World Skygreen!");
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Greeting message, @Headers MessageHeaders headers) throws Exception {
        Thread.sleep(1000); // simulated delay
        String name = message.getName();
        String msg;
        Map<String, List<String>> map = (Map<String, List<String>>) headers
        .get(NativeMessageHeaderAccessor.NATIVE_HEADERS);
        String accessToken = "";
        if (map != null && map.get("accessToken") != null) {
            accessToken = map.get("accessToken").get(0);
        }


        if (!StringUtils.isEmpty(name)) {
            customerService.setName(message.getName());
        }

        msg = message.getMessage();
        if (!StringUtils.isEmpty(msg)) {
            customerService.setMsg(accessToken, msg);
        }else{
            msg = name;
        }

        logger.debug( "Receive Msg Info, " + msg +  "!");
        return new Greeting("Hello, " + msg + "!");
    }

    @MessageMapping("/tick")
    public String tick(String message, @Headers MessageHeaders headers)
            throws Exception {
//        Map<String, List<String>> map= (Map<String, List<String>>) headers.get(NativeMessageHeaderAccessor.NATIVE_HEADERS);
//        if (map != null && map.get("Access-token") != null) {
//            String accessToken = map.get("Access-token").get(0);
//            msgHandleService.recordHeartbeat(accessToken);
//        }
//        logger.debug("message:" + message);
        return "1";
    }
}