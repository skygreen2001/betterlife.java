package com.zyp.bb;

import com.zyp.bb.message.basic.MsgSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by skygreen on 2017/8/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {

    @Autowired
    MsgSender msgSender;

    @Before
    public void setup() {

    }

    @Test
    public void runTest(){
        this.run();
    }

    public void run() {
        msgSender.send();
    }

}
