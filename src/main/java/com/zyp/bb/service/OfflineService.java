package com.zyp.bb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyp.bb.domain.BbMsg;
import com.zyp.bb.domain.BbUser;
import com.zyp.bb.util.UtilsCommon;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skygreen on 04/09/2017.
 */
@Service
public class OfflineService {

//    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    private BbUser bbUser;

    @Autowired
    private BbMsg bbMsg;

    @Autowired
    private SimpMessagingTemplate template;

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 接收消息的用户是否已经登录
     * @param userId
     * @return
     */
    public boolean isUserLogin(Long userId){
        String access_token = bbUser.get(userId);
        if (StringUtils.isEmpty(access_token)) return false;
        return true;
    }

    /**
     * 离线用户信息备份
     * @param userId
     */
    public void bakUserMsg(Long userId, JSONObject object){
        try {
            JSONObject data = object.optJSONObject("data");
            if (data!=null) {
                data.put("createtime", UtilsCommon.now());
            }
            bbMsg.append(userId, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理离线消息
     * @param access_token
     */
    public void handleOfflineMsgs(String access_token) {
        Long userId = bbUser.getUserId(access_token);
        if (userId!=null){
//            logger.debug("send msg to token:"+access_token);
            List<JSONObject> msgs = bbMsg.getAll(userId);
            if (msgs!=null && msgs.size()>0) {
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
                if (pgMsg != null && pgMsg.size() > 0) {
                    template.convertAndSendToUser(access_token, "/queue/offline", pgMsg);
//                    logger.debug(pgMsg.toString());
                }
            }
        }
    }
}
