package com.zyp.bb.message.amqp.object;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by skygreen on 2017/8/17.
 */
@Component
public class ReceiveObject {
    @Value("${queue.greet}")
    private String queueGreet;

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter) {//, MessageConverter messageConverter
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueGreet);
        container.setMessageListener(listenerAdapter);
        container.setMessageConverter(jsonMessageConverter());
        return container;
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new JsonMessageConverter();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ReceiveWork receiveWork) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiveWork, "receiveMessage");
        messageListenerAdapter.setMessageConverter(jsonMessageConverter());
        return messageListenerAdapter;
    }
}
