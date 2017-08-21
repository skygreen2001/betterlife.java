package com.zyp.bb.message.basic.t2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by skygreen on 2017/8/21.
 * 参考:[轻松搞定RabbitMQ-工作队列之消息分...](https://www.kancloud.cn/longxuan/rabbitmq-arron/117512)
 *     [轻松搞定RabbitMQ—消息应答与消息持久...](https://www.kancloud.cn/longxuan/rabbitmq-arron/117514)
 * 官网示例:[Work Queues](https://www.rabbitmq.com/tutorials/tutorial-two-java.html)
 */
@Component
public class T2MsgSender {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${queue.basic}")
    private String queue_basic;

    public static final void main(String[] args){
        T2MsgSender ts = new T2MsgSender("127.0.0.1", "HelloQ");
        ts.send();
    }

    public T2MsgSender(String host,String queue_basic){
        this.host = host;
        this.queue_basic = queue_basic;
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

            boolean durable = true;
            channel.queueDeclare(queue_basic, durable, false, false, null);
            int prefetchCount = 1;
            //限制发给同一个消费者不得超过1条消息
            channel.basicQos(prefetchCount);
            // 发送的消息
            String message;
            // 往队列中发出五条消息
            for (int i = 0; i < 5; i++) {
                // 发送的消息
                message = "Hello World"+StringUtils.repeat(".",i);
                // 往队列中发出一条消息
                channel.basicPublish("", queue_basic, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
            for (int i = 0; i < 5; i++) {
                // 发送的消息
                message = "Hello World"+StringUtils.repeat(".",5-i)+(5-i);
                // 往队列中发出一条消息
                channel.basicPublish("", queue_basic, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
            // 关闭频道和连接
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
