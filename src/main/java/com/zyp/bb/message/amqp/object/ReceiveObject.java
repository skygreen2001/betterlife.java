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
//        container.setMessageConverter(messageConverter);
        container.setMessageConverter(jsonMessageConverter());
        return container;
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new JsonMessageConverter();
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

        messageListenerAdapter.setMessageConverter(jsonMessageConverter());
        return messageListenerAdapter;
    }



//    @RabbitListener(queues = "${queue.greet}")
//    public void processMessage(Greeting message) {
//        logger.debug("[Receiver BB Object Report]" + message.getMessage());
//    }
}
