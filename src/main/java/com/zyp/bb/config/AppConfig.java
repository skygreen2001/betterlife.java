package com.zyp.bb.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Configuration
@ConfigurationProperties //[查看配置] (http://localhost:8080/configprops)
@EnableGlobalMethodSecurity
// @EnableSwagger2 //[API documentation](http://localhost:8080/swagger-ui.html)
@EnableAsync
@EnableScheduling
public class AppConfig implements ServletContextInitializer {

    private static WebApplicationContext webApplicationContext;

    /**
     * [SpringBoot ContextLoader.getCurrentWebApplicationContext() null
     * 解决方案]http://www.jianshu.com/p/125258ada53b
     *
     * @return
     */
    public static WebApplicationContext getCurrentWebApplicationContext() {
        return webApplicationContext;
    }

    /**
     * 在启动时将servletContext 获取出来，后面再读取二次使用。
     *
     * @param servletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

    @Bean
    public StompConnectEvent stompConnectEvent() {
        return new StompConnectEvent();
    }

    class StompConnectEvent implements ApplicationListener<SessionConnectEvent> {
        @SuppressWarnings("unchecked")
        @Override
        public void onApplicationEvent(SessionConnectEvent event) {
            WebApplicationContext webApplicationContext = AppConfig.getCurrentWebApplicationContext();
            ServletContext sc = webApplicationContext.getServletContext();
            Object obj = sc.getAttribute("users");
            Map<String, String> users = (Map<String, String>) (obj == null ? new HashMap<String, String>() : obj);
            StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage()); // 获取消息头
            if (headers.getNativeHeader("accessToken") != null) {
                String name = headers.getNativeHeader("accessToken").get(0); // 获取账号名
                users.put(name, headers.getSessionId());
                sc.setAttribute("users", users); // 将用户信息已map格式放存放起来
            }
        }
    }

}
