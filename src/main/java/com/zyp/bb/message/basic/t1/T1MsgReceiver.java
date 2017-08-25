package com.zyp.bb.message.basic.t1;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by skygreen on 2017/8/21.
 * 参考:[轻松搞定RabbitMQ](https://www.kancloud.cn/longxuan/rabbitmq-arron/117512)
 * 官网示例:[Hello World!](https://www.rabbitmq.com/tutorials/tutorial-one-python.html)
 */
@Component
public class T1MsgReceiver {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${app.queue.basic}")
    private String queue_basic;


    public void receive() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        // 打开连接和创建频道，与发送端一样
        Connection connection = null;

        try {
//            queue_basic = "hello";
            connection = factory.newConnection();
            Channel channel = connection.createChannel();

            // 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
            channel.queueDeclare(queue_basic, false, false, false, null);

            boolean autoAck = false;
            channel.basicConsume(queue_basic, autoAck, "myConsumerTag",
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body)
                            throws IOException {
//                            String routingKey = envelope.getRoutingKey();
//                            String contentType = properties.getContentType();
                        long deliveryTag = envelope.getDeliveryTag();
//                             (process the message components here ...)
                        String message = new String(body);
                        System.out.println(" [x] Received '" + message + "'");
                        channel.basicAck(deliveryTag, false);
                    }
                }
            );
        } catch (TimeoutException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

}
