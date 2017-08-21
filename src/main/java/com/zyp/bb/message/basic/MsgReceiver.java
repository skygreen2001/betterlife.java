package com.zyp.bb.message.basic;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by skygreen on 2017/8/21.
 */
public class MsgReceiver {

    @Value("${queue.basic}")
    private String queue_basic;
}
