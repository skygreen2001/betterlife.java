package com.zyp.bb.message.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Bean
    public Queue queue() {
        return new Queue("CustomerQ", false);
    }

    @RabbitListener(queues = "CustomerQ")
    public void processMessage(String message) {
        logger.debug("[Receiver BB Report]" + message);
    }
}