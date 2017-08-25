package com.zyp.bb.message.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Sender {
//    private final Logger logger = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private RabbitTemplate template;
//    RabbitMessagingTemplate template;

    @Value("${app.queue.normal}")
    private String queueNormal;

    @Bean
    public Queue queue() {
        return new Queue(queueNormal, true);
    }

    public void send(String message) {
//        template.convertAndSend(queueNormal, "[BB]:"+message);
        template.convertSendAndReceive(queueNormal, "[BB]:"+message);
    }

    public void sendJsonString(String message) {
        template.convertSendAndReceive(queueNormal, message);
    }
}