package com.zyp.bb.message.amqp;

import com.zyp.bb.domain.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {
    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Value("${queue.greet}")
    private String queueGreet;

//    @Bean
//    public Queue queue() {
//        return new Queue(queueNormal, false);
//    }

    @RabbitListener(queues = "${queue.normal}")
    public void processMessage(String message) {
        logger.debug("[Receiver BB Report]" + message);
    }


    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter) {//, MessageConverter messageConverter
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueGreet);
        container.setMessageListener(listenerAdapter);
//        container.setMessageConverter(messageConverter);
        return container;
    }

//    @Bean
//    public MessageConverter messageConverter() {
//        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
////        jackson2JsonMessageConverter.setClassMapper(new ClassMapper() {
////
////			@Override
////			public void fromClass(Class<?> arg0, MessageProperties arg1) {
////			}
////
////			@Override
////			public Class<?> toClass(MessageProperties arg0) {
////				return null;
////			}
////
////        });
//        return jackson2JsonMessageConverter;
//    }

    @Bean
    MessageListenerAdapter listenerAdapter(ReceiveWork receiveWork) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiveWork, "receiveMessage");
//        messageListenerAdapter.setMessageConverter(messageConverter());
        return messageListenerAdapter;
    }



//    @RabbitListener(queues = "${queue.greet}")
//    public void processMessage(Greeting message) {
//        logger.debug("[Receiver BB Object Report]" + message.getMessage());
//    }


}