package com.zyp.bb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyp.bb.domain.BbUser;
import com.zyp.bb.util.UtilsCommon;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by skygreen on 06/09/2017.
 */
@Service
public class MsgHandleService {
    private final Logger logger = LoggerFactory.getLogger(MsgHandleService.class);

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private BbUser bbUser;

    @Autowired
    private OfflineService offlineService;

    private ObjectMapper mapper = new ObjectMapper();

    private Object result = null;

    private int h1;

    /**
     * 发送心跳消息
     *
     * @throws Exception
     */
    @Scheduled(fixedRate = 10000)
    @Lazy(false)
    public void heartbeat() throws Exception {
        h1 += 1;
        logger.debug("Hello, Heart Beat " + h1 + "!");

//        template.convertAndSend("/tick", "1");

        Map<Long, String> accessTokes = bbUser.getAllUsers();
        for (Long userId:
                accessTokes.keySet()) {
            template.convertAndSendToUser(bbUser.get(userId), "/tick", "1");
            logger.debug("Hello, Heart Beat " + h1 + "![userId]:"+userId+",[accessToken]:" + bbUser.get(userId));
//            logger.debug("key = " + userId + ", value = " + accessToken);
        }

//        for (String accessToken:
//                accessTokes.values()) {
//            template.convertAndSendToUser(accessToken, "/tick", "1");
//            logger.debug("Hello, Heart Beat " + h1 + "![accessToken]:" + accessToken);
////            logger.debug("key = " + userId + ", value = " + accessToken);
//        }
    }

    /**
     * 处理接收到的消息
     */
    public void handleMsg(String message){
        if (UtilsCommon.isJsonString(message)) {
            try {
                JSONObject data = new JSONObject(message);
                data.put("flag","master");
                data.put("nick_name", "skygreen");
                logger.debug("[Receiver BB JSONObject Report]" + data);
                Long userId = data.optLong("receiver");
                if(offlineService.isUserLogin(userId)) {
                    try {
                        result = mapper.readValue(data.toString(), Object.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    offlineService.bakUserMsg(userId, data);
                }
                template.convertAndSend("/topic/greetings", result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理登录信息
     * @param message
     */
    public void handleLogin(String message){

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
