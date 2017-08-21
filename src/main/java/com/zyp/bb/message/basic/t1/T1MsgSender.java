package com.zyp.bb.message.basic.t1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by skygreen on 2017/8/16.
 * 参考:[轻松搞定RabbitMQ](https://www.kancloud.cn/longxuan/rabbitmq-arron/117512)
 * 官网示例:[Hello World!](https://www.rabbitmq.com/tutorials/tutorial-one-python.html)
 */
@Component
public class T1MsgSender {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${queue.basic}")
    private String queue_basic;

    public T1MsgSender(){

    }

    public void send() {
        /**
         * 创建连接连接到MabbitMQ
         */
        ConnectionFactory factory = new ConnectionFactory();
        // 设置MabbitMQ所在主机ip或者主机名
        factory.setHost(host);
        // 创建一个连接
        Connection connection = null;
        try {
            connection = factory.newConnection();
            // 创建一个频道
            Channel channel = connection.createChannel();
            // 指定一个队列
            channel.queueDeclare(queue_basic, false, false, false, null);
            // 发送的消息
            String message = "hello world! Skygreen";
            // 往队列中发出一条消息
            channel.basicPublish("", queue_basic, null, message.getBytes());
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
