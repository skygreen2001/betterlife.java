package com.zyp.bb.respository;

import com.zyp.bb.domain.BbUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Created by skygreen on 24/08/2017.
 * [参考:Using Redis with Spring](https://dzone.com/articles/using-redis-spring)
 */
@Repository
public class BbUserRepository {

    private final static String bbuser_key = "bb-user";
    private final Logger logger = LoggerFactory.getLogger(BbUserRepository.class);

    private RedisTemplate< String, Object > redisTemplate;

    private Map<String, Long> userInfos= new HashMap<>();

    private HashOperations hashOps;

    @Autowired
    public BbUserRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init() {
        hashOps = redisTemplate.opsForHash();
    }

    /**
     * 添加或者编辑用户登录信息
     * @param userId
     * @param accessToken
     */
    public void set( final Long userId, final String accessToken ) {
        if (userId!=null && userId>0) {
            BbUser bbUser = this.get(userId);
            if (!StringUtils.isEmpty(accessToken)) {
                if (bbUser == null) {
                    bbUser = new BbUser(userId, accessToken);
                }
                bbUser.setOnline(true);
                bbUser.setLastTime(ZonedDateTime.now());
                userInfos.put(accessToken, userId);
            } else {
                if (bbUser != null) bbUser.setOnline(false);
            }
            hashOps.put(bbuser_key, userId, bbUser);
        }
    }

    public void update(final String accessToken, boolean status)
    {
        BbUser bbUser = this.get(accessToken);
        if (bbUser != null){
            if (bbUser.isOnline()!=status) {
                bbUser.setOnline(status);
                hashOps.put(bbuser_key, bbUser.getUserId(), bbUser);
            }
        }
    }


    public void update(final String accessToken, ZonedDateTime lastTime)
    {
        BbUser bbUser = this.get(accessToken);
        if (bbUser != null){
            bbUser.setLastTime(lastTime);
            hashOps.put(bbuser_key, bbUser.getUserId(), bbUser);
        }
    }

    /**
     * 获取指定用户编号的用户信息
     *
     * @param userId
     * @return
     */
    public BbUser get(final Long userId) {
        if (userId!=null && userId>0) {
            return (BbUser) hashOps.get(bbuser_key, userId);
        }else {
            return null;
        }
    }

    /**
     * 获取指定用户accessToken的用户信息
     *
     * @param accessToken
     * @return
     */
    public BbUser get(final String accessToken) {
        Long userId = this.getUserId(accessToken);
        if (userId!=null && userId>0) {
            return this.get(userId);
        } else {
            return null;
        }
    }


    /**
     * 获取指定用户编号的离线信息
     *
     * @param userId
     * @return
     */
    public boolean isUserOffline(final Long userId) {
        boolean result = false;
        if (userId!=null && userId>0) {
            BbUser bbUser = this.get(userId);
            if (bbUser == null) return true;
            if (bbUser != null && bbUser.isOnline() == false) return true;
        }else{
            return true;
        }
        return result;
    }

    /**
     * 根据Accesstoken获取指定用户编号
     *
     * @param accessToken
     * @return
     */
    public Long getUserId(final String accessToken) {
        Long userId = null;
        if (!StringUtils.isEmpty(accessToken)) {
            //先从缓存中获取用户编号
            userId = this.userInfos.get(accessToken);

            if (userId == null) {
                //如果没有,再从Redis持久化缓存中获取用户编号
                Map<Object, Object> userIds = hashOps.entries(bbuser_key);
                BbUser bbUser;
                if (userIds != null && userIds.size() > 0) {
                    for (Object userIdTmp : userIds.keySet()) {
                        bbUser =  (BbUser) hashOps.get(bbuser_key, userIdTmp);
                        if (bbUser.getAccessToken().equals(accessToken)) {
                            userId = bbUser.getUserId();
                            this.userInfos.put(accessToken, userId);
                            logger.debug("getUserId: key = " + userId + ", value = " + accessToken);
                            break;
                        }
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
        Map<Object, Object> userIds = hashOps.entries(bbuser_key);
        return userIds.size();
    }

    /**
     * 获取所有的用户
     * @return 每个元素为AccessToken
     */
    public Map<Long, String> getAllUsers(){
        Map<Object, Object> userIds = hashOps.entries(bbuser_key);
        BbUser bbUser;
        Long userId;
        Map<Long, String> result = new HashMap<>();
        for (Object userIdTmp : userIds.keySet()) {
            bbUser =  (BbUser) hashOps.get(bbuser_key, userIdTmp);
            userId = bbUser.getUserId();
            if (!this.isUserOffline(userId)) result.put(userId, bbUser.getAccessToken());
        }
        return result;
    }
    /**
     * 获取所有在线的用户
     *
     * @return 每个元素为AccessToken
     */
    public Map<Long, BbUser> getAllUserDetails() {
        Map<Object, Object> userIds = hashOps.entries(bbuser_key);
        BbUser bbUser;
        Long userId;
        Map<Long, BbUser> result = new HashMap<>();
        for (Object userIdTmp : userIds.keySet()) {
            bbUser =  (BbUser) hashOps.get(bbuser_key, userIdTmp);
            userId = bbUser.getUserId();
            if (!this.isUserOffline(userId)) result.put(userId, bbUser);
        }
        return result;
    }

    /**
     * 重启加载所有用户到缓存里
     */
    public void loadAllLoginUsers() {
        Map<Object, Object> userIds = hashOps.entries(ittruser_key);
        IttrUser ittrUser;
        Long userId;
        Map<Long, IttrUser> result = new HashMap<>();
        for (Object userIdTmp : userIds.keySet()) {
            ittrUser =  (IttrUser) hashOps.get(ittruser_key, userIdTmp);
            userId = ittrUser.getUserId();
            userInfos.put(ittrUser.getAccessToken(), userId);
            logger.debug("load all login users: key = " + userId + ", value = " + ittrUser.getAccessToken());
        }
    }

    /**
     * 报告登录用户信息
     * @return
     */
    public void report(){
        Map<Long, String> accessTokes = this.getAllUsers();
        String accessToken;
        for (Long userId:
                accessTokes.keySet()) {
            accessToken = accessTokes.get(userId);
            if (!this.isUserOffline(userId))
                logger.debug("key = " + userId + ", value = " + accessToken);
        }
    }
}
