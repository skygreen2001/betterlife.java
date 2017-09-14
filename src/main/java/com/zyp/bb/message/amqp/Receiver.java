package com.zyp.bb.message.amqp;

import com.zyp.bb.service.MsgHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private MsgHandleService msgService;

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
            Thread.sleep(11000); // simulated delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        msgService.handleOfflineMsgs(message);
    }

}