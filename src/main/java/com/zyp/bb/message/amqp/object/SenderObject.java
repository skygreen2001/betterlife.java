package com.zyp.bb.message.amqp.object;

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

    @Value("${app.queue.greet}")
    private String queueGreet;

    @Bean
    public Queue queueGreet() {
        return new Queue(queueGreet, true);
    }

    public void send(Greeting message) {
//        template.convertAndSend(queueGreet, message);
        template.convertSendAndReceive(queueGreet, message);

    }

}
