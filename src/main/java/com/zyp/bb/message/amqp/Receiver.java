package com.zyp.bb.message.amqp;

import com.rabbitmq.client.ConnectionFactory;
import com.zyp.bb.Application;
import com.zyp.bb.domain.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

//    @Value("${queue.normal}")
//    private String queueNormal;
//
//    @Bean
//    public Queue queue() {
//        return new Queue(queueNormal, false);
//    }

    @RabbitListener(queues = "${queue.normal}")
    public void processMessage(String message) {
        logger.debug("[Receiver BB Report]" + message);
    }

    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

//    @RabbitListener(queues = "${queue.greet}")
//    public void processMessage(Greeting message) {
//        logger.debug("[Receiver BB Object Report]" + message.getMessage());
//    }
}