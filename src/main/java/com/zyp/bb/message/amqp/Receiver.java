package com.zyp.bb.message.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyp.bb.domain.BbUser;
import com.zyp.bb.util.UtilsCommon;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Receiver {
    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private BbUser bbUser;

    @Value("${app.queue.loginQ}")
    private String queueLogin;

    @Autowired
    private SimpMessagingTemplate template;

    private ObjectMapper mapper = new ObjectMapper();

    private Object result = null;

    @RabbitListener(queues = "${app.queue.normal}")
    public void processMessage(String message) {

        if (UtilsCommon.isJsonString(message)) {
            try {
                JSONObject data = new JSONObject(message);
                data.put("flag","master");
                data.put("nick_name", "skygreen");
                logger.debug("[Receiver BB JSONObject Report]" + data);
                try {
                    result = mapper.readValue(data.toString(),Object.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                template.convertAndSend("/topic/greetings", result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        logger.debug("[Receiver BB Report]" + message);
    }

    @RabbitListener(queues = "${app.queue.loginQ}")
    public void processLoginMessage(String message) {
        logger.debug("[Receiver Login Msg]" + message);

        if (UtilsCommon.isJsonString(message)) {
            try {
                JSONObject object = new JSONObject(message);
                String access_token = object.optString("access_token");
                long user_id = object.optLong("user_id");
                bbUser.set(user_id, access_token);
                bbUser.report();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}