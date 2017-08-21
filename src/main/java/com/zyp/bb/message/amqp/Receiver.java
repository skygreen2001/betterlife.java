package com.zyp.bb.message.amqp;

import com.zyp.bb.util.UtilsCommon;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    @RabbitListener(queues = "${queue.normal}")
    public void processMessage(String message) {

        if (UtilsCommon.isJsonString(message)) {
            try {
                JSONObject object = new JSONObject(message);
                logger.debug("[Receiver BB JSONObject Report]" + object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        logger.debug("[Receiver BB Report]" + message);
    }


}