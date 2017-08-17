package com.zyp.bb.message.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Sender {
//    private final Logger logger = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private RabbitTemplate template;
//    RabbitMessagingTemplate template;

    @Value("${queue.normal}")
    private String queueNormal;

//    @Autowired
//    public Sender(RabbitMessagingTemplate template) {
//        this.template = template;
//    }

//    @Autowired
//    public Sender(RabbitTemplate template) {
//        this.template = template;
//    }

    @Bean
    public Queue queue() {
        return new Queue(queueNormal, false);
    }

    public void send(String message) {
        template.convertAndSend(queueNormal, "[BB]:"+message);
    }



}