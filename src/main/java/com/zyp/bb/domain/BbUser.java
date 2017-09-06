package com.zyp.bb.domain;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by skygreen on 24/08/2017.
 * [参考:Using Redis with Spring](https://dzone.com/articles/using-redis-spring)
 */
@Component
public class BbUser implements Serializable {

    private final static String prefix_key = "bb-user:";

    private final static String suffix_key = "-bbmsg";
    private final Logger logger = LoggerFactory.getLogger(BbUser.class);

    @Autowired
    private RedisTemplate< String, Object > template;

    private Map<String, Long> userInfos= new HashMap<>();

    /**
     * 添加或者编辑用户登录信息
     * @param userId
     * @param accessToken
     */
    public void set( final Long userId, final String accessToken ) {
        final String key = String.format(prefix_key + "%s", userId );
        template.opsForValue().set(key,accessToken);
        userInfos.put(accessToken, userId);
    }

    /**
     * 获取指定用户编号的登录信息
     * @param userId
     * @return
     */
    public String get( final Long userId) {
        final String key = String.format( prefix_key + "%s", userId );
        final String result = ( String )template.opsForValue().get(key);
        return result;
    }

    /**
     * 根据Accesstoken获取指定用户编号
     * @param accessToken
     * @return
     */
    public Long getUserId( final String accessToken) {
        Long userId = null;
        if(!StringUtils.isEmpty(accessToken)) {
            //先从缓存中获取用户编号
            userId = this.userInfos.get(accessToken);

            if (userId == null) {
                //如果没有,再从Redis持久化缓存中获取用户编号
                Set userIds = template.keys(prefix_key+"*");
                String accessTokenTmp;
                if (userIds!=null && userIds.size()>0) {
                    for (Object userIdTmp : userIds) {
                        accessTokenTmp = (String) template.opsForValue().get(userIdTmp);
                        if (accessTokenTmp.equals(accessToken)) {
                            String userIdStr = (String) userIdTmp;
                            userIdStr = userIdStr.replace(prefix_key, "");
                            userId = Long.getLong(userIdStr);
                            break;
                        }
                        logger.debug("key = " + userId + ", value = " + accessTokenTmp);
                    }
                }
            }
        }
        return userId;
    }

    /**
     * 获取所有登录过用户数
     * @return
     */
    public int size(){
        Set userIds = template.keys(prefix_key+"*");
        return userIds.size();
    }

    /**
     * 报告登录用户信息
     * @return
     */
    public void report(){
        Set userIds = template.keys(prefix_key+"*");
        String accessToken;
        for (Object userId:userIds) {
            accessToken = ( String )template.opsForValue().get(userId);
            logger.debug("key = " + userId + ", value = " + accessToken);
        }
    }
}
