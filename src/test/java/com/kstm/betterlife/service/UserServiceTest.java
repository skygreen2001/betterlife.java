package com.kstm.betterlife.service;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.kstm.betterlife.domain.User;

//@ActiveProfiles("test")
@Test
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/java/root-context.xml",
        "file:src/main/resources/spring/appServlet/servlet-context.xml" })
//@ContextConfiguration(locations = {"file:src/test/java/root-context.xml","classpath:/spring/appServlet/servlet-context.xml"})
public class UserServiceTest extends AbstractTestNGSpringContextTests
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
