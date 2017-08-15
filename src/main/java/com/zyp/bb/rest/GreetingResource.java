package com.zyp.bb.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyp.bb.domain.Greeting;
import com.zyp.bb.service.CustomerService;

@RestController
public class GreetingResource {

    private final Logger logger = LoggerFactory.getLogger(GreetingResource.class);

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/greet")
    public Greeting greet() {
        return new Greeting("Hello World Skygreen!");
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Greeting message, @Headers MessageHeaders headers) throws Exception {
        Thread.sleep(1000); // simulated delay
        String msg = message.getName();

        @SuppressWarnings("unchecked")
        Map<String, List<String>> map = (Map<String, List<String>>) headers
        .get(NativeMessageHeaderAccessor.NATIVE_HEADERS);

        if (map != null && map.get("accessToken") != null) {
            String userID = map.get("accessToken").get(0);
            customerService.setName(userID, message.getName());
        }
        logger.debug( "Receive Msg Info, " + msg +  "!");
        return new Greeting("Hello, " + msg + "!");

    }
}