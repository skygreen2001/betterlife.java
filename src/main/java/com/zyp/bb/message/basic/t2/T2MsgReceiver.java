package com.zyp.bb.message.basic.t2;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * Created by skygreen on 2017/8/21.
 * 参考:[轻松搞定RabbitMQ-工作队列之消息分...](https://www.kancloud.cn/longxuan/rabbitmq-arron/117512)
 *     [轻松搞定RabbitMQ—消息应答与消息持久...](https://www.kancloud.cn/longxuan/rabbitmq-arron/117514)
 * 官网示例:[Work Queues](https://www.rabbitmq.com/tutorials/tutorial-two-java.html)
 */
@Component
public class T2MsgReceiver {


    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${queue.basic}")
    private String queue_basic;

    public static final void main(String[] args){
        T2MsgReceiver tr = new T2MsgReceiver("127.0.0.1", "HelloQ");
        tr.receive();
    }

    public T2MsgReceiver(String host,String queue_basic){
        this.host = host;
        this.queue_basic = queue_basic;
    }

    public void receive() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        // 打开连接和创建频道，与发送端一样
        Connection connection = null;

        try {
            connection = factory.newConnection();
            Channel channel = connection.createChannel();
            // 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
            boolean durable = true;
            channel.queueDeclare(queue_basic, durable, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            channel.basicQos(1);//保证一次只分发一个

            boolean autoAck = false;
            // 创建队列消费者
            channel.basicConsume(queue_basic, autoAck, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");

                    System.out.println(" [x] Received '" + message + "'");

                    System.out.println(" [x] Proccessing... at " + DateFormat.getDateTimeInstance().format(new Date()));
                    try {
                        for (char ch : message.toCharArray()) {
                            if (ch == '.') {
                                Thread.sleep(1000);
                            }
                        }
                    } catch (InterruptedException e) {
                    } finally {
                        System.out.println(" [x] Done! at " + DateFormat.getDateTimeInstance().format(new Date()));
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            });

        } catch (TimeoutException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
