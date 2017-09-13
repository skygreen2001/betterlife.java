package com.zyp.bb.message.amqp;

import com.zyp.bb.service.MsgHandleService;
import com.zyp.bb.service.OfflineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Value("${app.queue.loginQ}")
    private String queueLogin;

    @Autowired
    private MsgHandleService msgService;

    @Autowired
    private OfflineService offlineService;

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

    @RabbitListener(queues = "${app.queue.leaveQ}")
    public void processLeaveMsg(String message) {
        logger.debug("[Receiver Websocket Leave Msg]" + message);
        msgService.handleLeave(message);
    }

    @RabbitListener(queues = "${app.queue.goQ}")
    public void processGoMsg(String message) {
        logger.debug("[Receiver Login Msg]" + message);
        try {
            Thread.sleep(11000); // simulated delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        msgService.handleGo(message);
        offlineService.handleOfflineMsgs(message);
    }

}