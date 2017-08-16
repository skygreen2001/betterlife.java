package com.zyp.bb.message.amqp;

import com.zyp.bb.domain.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Sender {
//    private final Logger logger = LoggerFactory.getLogger(Sender.class);

    RabbitMessagingTemplate template;

    @Value("${queue.normal}")
    private String queueNormal;

    @Value("${queue.greet}")
    private String queueGreet;

    @Autowired
    public Sender(RabbitMessagingTemplate template) {
        this.template = template;
    }

    @Bean
    public Queue queue() {
        return new Queue(queueNormal, false);
    }


    public void send(String message) {
        template.convertAndSend(queueNormal, "[BB]:"+message);

    }

    public void send(Greeting message) {
        template.convertAndSend(queueGreet, message);
    }
}