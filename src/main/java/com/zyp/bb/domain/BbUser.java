package com.zyp.bb.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Created by skygreen on 14/09/2017.
 */
public class BbUser implements Serializable {
    private Long userId;
    private String accessToken;
    private boolean isOnline;
    private ZonedDateTime lastTime;

    public BbUser(Long userId, String accessToken) {
        this.setUserId(userId);
        this.setAccessToken(accessToken);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        this.isOnline = online;
    }

    public ZonedDateTime getLastTime() {
        return lastTime;
    }

    public void setLastTime(ZonedDateTime lastTime) {
        this.lastTime = lastTime;
    }

}
