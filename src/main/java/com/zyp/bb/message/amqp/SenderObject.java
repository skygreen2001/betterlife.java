package com.zyp.bb.message.amqp;

import com.zyp.bb.domain.Greeting;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created by skygreen on 2017/8/17.
 */
@Component
@Lazy
public class SenderObject {
    @Autowired
    private RabbitTemplate template;
//  private  RabbitMessagingTemplate template;

    @Value("${queue.greet}")
    private String queueGreet;

    @Bean
    public Queue queueGreet() {
        return new Queue(queueGreet, false);
    }

    public void send(Greeting message) {
//        template.convertAndSend(queueGreet, message);
        template.convertAndSend(queueGreet, message.getMessage());

//        template.setMessageConverter(new SimpleMessageConverter());
//        template.convertAndSend(queueGreet, message, m -> {
//            m.getMessageProperties().setContentType("application/json");
//            return m;
//        });
    }

//    @Bean
//    TopicExchange exchange() {
//        return new TopicExchange("spring-boot-exchange");
//    }
//
//
//    @Bean
//    Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(queueGreet);
//    }

}
