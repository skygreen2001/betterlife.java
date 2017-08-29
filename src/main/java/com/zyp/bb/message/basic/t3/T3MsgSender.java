package com.zyp.bb.message.basic.t3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by skygreen on 28/08/2017.
 * 参考:[轻松搞定RabbitMQ-发布/订阅](https://www.kancloud.cn/longxuan/rabbitmq-arron/117515)
 * 官网示例:[Publish/Subscribe](https://www.rabbitmq.com/tutorials/tutorial-three-java.html)
 *
 */
@Component
public class T3MsgSender {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${app.queue.exchangeName}")
    private String exchangeName;

    public static void main(String[] argv) {

        T3MsgSender ts = new T3MsgSender();
        ts.host = "127.0.0.1";
        ts.exchangeName = "logs";
        ts.send();
    }

    public void send() {
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ所在主机ip或者主机名
        factory.setHost(host);

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(exchangeName, "fanout");

            double d = Math.random();
            int i = (int) (d * 1000000);
            String message = "public message is good!" + i;

            channel.basicPublish(exchangeName, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            channel.close();
            connection.close();

        } catch (TimeoutException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

}
