package com.zyp.bb.message.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Sender {
//    private final Logger logger = LoggerFactory.getLogger(Sender.class);

    RabbitMessagingTemplate template;

    @Autowired
    public Sender(RabbitMessagingTemplate template) {
        this.template = template;
    }

    @Bean
    public Queue queue() {
        return new Queue("CustomerQ", false);
    }

    public void send(String message) {
        template.convertAndSend("CustomerQ", message);
    }

}