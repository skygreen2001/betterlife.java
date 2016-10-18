package com.kstm.betterlife.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.kstm.betterlife.domain.User;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public int getMatchCount(String userName, String password) {
        String sqlStr = " SELECT count(*) FROM bb_user_user "
                + " WHERE username =? and password=? ";

        return jdbcTemplate.queryForObject(sqlStr, new Object[]{userName, password},Integer.class);
    }

    public User findUserByUserName(final String userName) {
        String sqlStr = " SELECT user_id,username "
                + " FROM bb_user_user WHERE username =? ";
        final User user = new User();
        jdbcTemplate.query(sqlStr, new Object[]{userName},
                new RowCallbackHandler() {
                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        user.setUserId(rs.getInt("user_id"));
                        user.setUserName(userName);
                    }
                });
        return user;
    }

    public void updateLoginInfo(User user) {
        String sqlStr = " UPDATE bb_user_user SET lastvisit=?,lastip=?"
                + " WHERE user_id =?";
        jdbcTemplate.update(sqlStr, new Object[]{user.getLastVisit(),
                user.getLastIp(), user.getUserId()});
    }

}
