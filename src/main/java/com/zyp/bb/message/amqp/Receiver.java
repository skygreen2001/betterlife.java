package com.zyp.bb.message.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    @RabbitListener(queues = "${queue.normal}")
    public void processMessage(String message) {
        logger.debug("[Receiver BB Report]" + message);
    }


}