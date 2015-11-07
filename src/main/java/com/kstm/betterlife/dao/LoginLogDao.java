package com.kstm.betterlife.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kstm.betterlife.domain.LoginLog;

@Repository
public class LoginLogDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertLoginLog(LoginLog loginLog) {
        String sqlStr = "INSERT INTO bb_log_loginlog(user_id,ip,login_date) "
                + "VALUES(?,?,?)";
        Object[] args = {loginLog.getUserId(), loginLog.getIp(),
                loginLog.getLoginDate()};
        jdbcTemplate.update(sqlStr, args);
    }
}