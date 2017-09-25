package com.zyp.bb.domain;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by skygreen on 05/09/2017.
 */
@Component
public class BbMsg implements Serializable {
    private final static String suffix_key = "-bbmsg";
    private final static String suffix_key_send = "-bb-sendmsg";

    @Autowired
    private RedisTemplate< String, Object > template;

    /**
     * 添加指定用户标识消息
     * @param userId
     * @param msg
     */
    public void append( final Long userId, final JSONObject msg ) {
        String key = String.format("%s" + suffix_key, userId);
        template.opsForList().rightPush(key, msg);
        template.expire(key, 7, TimeUnit.DAYS);//默认保存7天
    }

    /**
     * 备份发送的消息
     * @param userId
     * @param msg
     */
    public void appendSendMsg( final Long userId, final JSONObject msg ) {
        String key = String.format("%s" + suffix_key_send, userId);
        template.opsForList().rightPush(key, msg);
        template.expire(key, 7, TimeUnit.DAYS);//默认保存7天
    }

    /**
     * 删除确认已发送的消息
     * @param userId
     */
    public void deleteSendMsg( final Long userId ){
        final String key = String.format("%s" + suffix_key_send, userId);
        template.delete(key);
    }


    /**
     * 备份断线未发送的消息
     * @param userId
     */
    public void bakSendMsg( final Long userId ) {
        final String key = String.format("%s" + suffix_key_send, userId);
        List<Object> msgs  = template.opsForList().range(key,0, -1);

        String bak_key = String.format("%s" + suffix_key, userId);
        if (msgs!=null && msgs.size()>0){
            for (Object msg :
                    msgs) {
                template.opsForList().rightPush(bak_key, msg);
            }
        }
        template.delete(key);
    }

    /**
     * 获取指定用户标识的所有消息
     * @param userId
     * @return
     */
    public List<JSONObject> getAll(Long userId) {
        final String key = String.format("%s" + suffix_key, userId);
        List<Object> msgs  = template.opsForList().range(key,0, -1);
        List<JSONObject> result = null;
        JSONObject object;
        if (msgs!=null && msgs.size()>0){
            result=new ArrayList<>();
            for (Object msg :
                    msgs) {
                try {
                    object = new JSONObject(msg.toString());
                    result.add(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        template.delete(key);
        return result;
    }

    public void clearAllKeys( final Long userId, final JSONObject msg ) {
        template.getConnectionFactory().getConnection().flushAll();//清除所有keys
    }
}
