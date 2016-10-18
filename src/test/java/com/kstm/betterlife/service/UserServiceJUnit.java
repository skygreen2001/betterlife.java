package com.kstm.betterlife.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kstm.betterlife.domain.User;

@ContextConfiguration(locations = { "file:src/test/java/root-context.xml",
        "file:src/main/resources/spring/appServlet/servlet-context.xml" })
//@ContextConfiguration(locations = {"file:src/test/java/root-context.xml","classpath:/spring/appServlet/servlet-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceJUnit extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private UserService userService;

	@Test
	public void hasMatchUserTest() {
		boolean b1 = userService.hasMatchUser("admin", "admin");
		boolean b2 = userService.hasMatchUser("admin", "1111");
		assertTrue(b1);
		assertTrue(!b2);
	}

	@Test
    public void findUserByUserNameTest() {
        User user = userService.findUserByUserName("admin");
        assertEquals(user.getUserName(), "admin");
    }

	@Test
    public void loginSuccessTest() {
        User user = userService.findUserByUserName("admin");
        user.setUserId(1);
        user.setUserName("admin");
        user.setLastIp("192.168.1.127");
        user.setLastVisit(new Date());
        userService.loginSuccess(user);
    }

}
