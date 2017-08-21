package com.zyp.bb.message.amqp.object;

import com.zyp.bb.domain.Greeting;
import com.zyp.bb.message.amqp.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by skygreen on 2017/8/17.
 */

@Component
public class ReceiveWork {
    private final Logger logger = LoggerFactory.getLogger(Sender.class);

    private CountDownLatch latch = new CountDownLatch(1);

//    public void receiveMessage(String message) {
//        logger.debug("[Receiver BB Object Report]" + message);
//        latch.countDown();
//    }

    public void receiveMessage(Greeting message) {
        logger.debug("[Receiver BB Object Report]" + message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
