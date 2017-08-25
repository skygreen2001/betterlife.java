package com.zyp.bb;

import com.zyp.bb.message.basic.t1.T1MsgReceiver;
import com.zyp.bb.message.basic.t1.T1MsgSender;
import com.zyp.bb.message.basic.t2.T2MsgReceiver;
import com.zyp.bb.message.basic.t2.T2MsgSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * Created by skygreen on 2017/8/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RabbitMQTest {

    @Autowired
    T1MsgSender t1MsgSender;

    @Autowired
    T1MsgReceiver t1MsgReceiver;

    @Autowired
    T2MsgSender t2MsgSender;

    @Autowired
    T2MsgReceiver t2MsgReceiver;


    private CountDownLatch latch;

    @Before
    public void setup() {
    }

    @Test
    public void run1Send(){
        t1MsgSender.send();
    }

    @Test
    public void run1Receive() {
        t1MsgReceiver.receive();
    }

    @Test
    public void run2Send(){
        t2MsgSender.send();
    }

    @Test
    public void run2Receive() {
        latch = new CountDownLatch(1);

        t2MsgReceiver.receive();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
