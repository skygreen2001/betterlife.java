package com.zyp.bb.config.wss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Java Spring WebSocket消息交互: https://www.jianshu.com/p/f0cbfc1f0a50
 * @author skygreen2001
 */
public class WebSocketInterceptor implements HandshakeInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse arg1,
                                   WebSocketHandler arg2, Map<String, Object> arg3) throws Exception {
        // 将ServerHttpRequest转换成request请求相关的类，用来获取request域中的用户信息
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            logger.info(httpRequest.toString());
            HttpSession session = servletRequest.getServletRequest().getSession();
            logger.info(session.toString());
        }
        logger.info("beforeHandshake完成");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
        logger.info("afterHandshake完成");
    }
}