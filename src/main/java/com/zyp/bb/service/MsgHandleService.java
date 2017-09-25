package com.zyp.bb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyp.bb.domain.BbMsg;
import com.zyp.bb.domain.BbUser;
import com.zyp.bb.respository.BbUserRepository;
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
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private BbUserRepository bbUserDao;

    @Autowired
    private BbMsg bbMsg;

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

        Map<Long, String> accessTokes = bbUserDao.getAllUsers();

        for (Long userId :
                accessTokes.keySet()) {
            template.convertAndSendToUser(accessTokes.get(userId), "/tick", "1");
            logger.debug("Hello, Heart Beat " + h1 + "![userId]:" + userId + ",[accessToken]:" + bbUserDao.get(userId));
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
     * 发送心跳消息
     *
     * @throws Exception
     */
    @Scheduled(fixedRate = 25000)
    @Lazy(false)
    public void heartbeatTimeout() throws Exception {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime recordTime;
        BbUser ittrUser;
        Map<Long, BbUser> validUserTmpMaps = bbUserDao.getAllUserDetails();
        for (Long userId :
                validUserTmpMaps.keySet()) {
            ittrUser = validUserTmpMaps.get(userId);
            if (ittrUser.isOnline()) {
                recordTime = ittrUser.getLastTime();
                recordTime = recordTime.plusSeconds(25l);
                if (recordTime != null) {
                    if (recordTime.toEpochSecond() < now.toEpochSecond()) {
                        bbUserDao.update(ittrUser.getAccessToken(), false);
                    }
                }
            }
        }
    }

    /**
     * 处理接收到的消息
     */
    public void handleMsg(String message) {
        if (UtilsCommon.isJsonString(message)) {
            try {
                JSONObject data = new JSONObject(message);
                data.put("flag", "master");
                data.put("nick_name", "skygreen");
                logger.debug("[Receiver BB JSONObject Report]" + data);
                Long userId = data.optLong("receiver");
                if (userId!=null && userId>0) {
                    if (!bbUserDao.isUserOffline(userId)) {
                        try {
                            result = mapper.readValue(data.toString(), Object.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        this.bakUserMsg(userId, data, true);
                    } else {
                        this.bakUserMsg(userId, data, false);
                    }
                    if ( result!=null ) template.convertAndSend("/topic/greetings", result);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 记录用户心跳
     *
     * @param accessToken
     */
    public void recordHeartbeat(String accessToken)
    {
        bbUserDao.update(accessToken, ZonedDateTime.now());
        if (!StringUtils.isEmpty(accessToken)) {
            Long userId = bbUserDao.getUserId(accessToken);
            bbMsg.deleteSendMsg(userId);
        }
    }

    /**
     * 处理登录信息
     *
     * @param message
     */
    public void handleLogin(String message) {

        if (UtilsCommon.isJsonString(message)) {
            try {
                JSONObject object = new JSONObject(message);
                String access_token = object.optString("accessToken");
                long user_id = object.optLong("user_id");
                bbUserDao.set(user_id, access_token);
                bbUserDao.report();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理登出信息
     *
     * @param access_token
     */
    public void handleLeave(String access_token) {
        if (!StringUtils.isEmpty(access_token)) {
            bbUserDao.update(access_token, false);
            Long userId = bbUserDao.getUserId(access_token);
            if (userId != null) {
                bbMsg.bakSendMsg(userId);
            }
        }
        logger.debug("logout leave userId:" + access_token);
    }


    /**
     * 离线用户信息备份
     *
     * @param userId
     */
    public void bakUserMsg(Long userId, JSONObject object, boolean isOnline) {
        try {
            JSONObject data = object.optJSONObject("data");
            if (data != null) {
//                data.put("createtime", UtilsCommon.now());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//设置日期格式
                String now = df.format(new Date()) + ".000Z";
                data.put("createtime", now);
            }
            if (isOnline) {
                bbMsg.appendSendMsg(userId, object);
            }else{
                bbMsg.append(userId, object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理离线消息
     *
     * @param access_token
     */
    public void handleOfflineMsgs(String access_token) {
        Long userId = bbUserDao.getUserId(access_token);
        if (userId != null) {
            logger.debug("handleOfflineMsgs:token:" + access_token + ",userId:" + userId);
//            logger.debug("send msg to token:"+access_token);
            bbUserDao.set(userId, access_token);
            List<JSONObject> msgs = bbMsg.getAll(userId);
            if (msgs != null && msgs.size() > 0) {
                Object result;
                List<Object> pgMsg = new ArrayList<>();
                for (JSONObject msg :
                        msgs) {
                    JSONObject data = msg.optJSONObject("data");
                    try {
                        result = mapper.readValue(data.toString(), Object.class);
                        pgMsg.add(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    logger.debug("send msg to client :" + data.toString());
//                    logger.debug(data.toString());
                }
                logger.debug("handleOfflineMsgs:" + pgMsg.toString() + ",size:" + pgMsg.size());
                if (pgMsg != null && pgMsg.size() > 0) {
                    template.convertAndSendToUser(access_token, "/queue/offline", pgMsg);
//                    logger.debug(pgMsg.toString());
                }
            }
        }
    }
}
