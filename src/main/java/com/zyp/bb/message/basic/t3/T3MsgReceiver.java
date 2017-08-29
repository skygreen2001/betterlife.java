package com.zyp.bb.message.basic.t3;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by skygreen on 28/08/2017.
 * 参考:[轻松搞定RabbitMQ-发布/订阅](https://www.kancloud.cn/longxuan/rabbitmq-arron/117515)
 * 官网示例:[Publish/Subscribe](https://www.rabbitmq.com/tutorials/tutorial-three-java.html)
 */
public class T3MsgReceiver {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${app.queue.exchangeName}")
    private String exchangeName;



    public static void main(String[] argv) throws Exception {

        T3MsgReceiver tr = new T3MsgReceiver();
        tr.host = "127.0.0.1";
        tr.exchangeName = "logs";
        tr.receive();
    }

    public void receive() {

        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ所在主机ip或者主机名
        factory.setHost(host);

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(exchangeName, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, exchangeName, "");

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };
            channel.basicConsume(queueName, true, consumer);

        } catch (TimeoutException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
