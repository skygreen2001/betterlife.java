package com.zyp.bb.message.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyp.bb.domain.BbUser;
import com.zyp.bb.service.MsgHandleService;
import com.zyp.bb.service.OfflineService;
import com.zyp.bb.util.UtilsCommon;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Receiver {
    private final Logger logger = LoggerFactory.getLogger(Receiver.class);


    @Autowired
    private MsgHandleService msgService;

    @Value("${app.queue.loginQ}")
    private String queueLogin;

    @Autowired
    private OfflineService offlineService;

    @Autowired
    private SimpMessagingTemplate template;

    private ObjectMapper mapper = new ObjectMapper();

    private Object result = null;

    @Bean
    public Queue queueLogin() {
        return new Queue(queueLogin, true);
    }

    @RabbitListener(queues = "${app.queue.normal}")
    public void processMessage(String message) {
        logger.debug("[Receiver BB Report]" + message);
        msgService.handleMsg(message);
    }

    @RabbitListener(queues = "${app.queue.loginQ}")
    public void processLoginMessage(String message) {
        logger.debug("[Receiver Login Msg]" + message);
        msgService.handleLogin(message);
    }

    @RabbitListener(queues = "${app.queue.goQ}")
    public void processGoMsg(String message) {
        logger.debug("[Receiver Login Msg]" + message);
        try {
            Thread.sleep(1000); // simulated delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        offlineService.handleOfflineMsgs(message);
    }

}