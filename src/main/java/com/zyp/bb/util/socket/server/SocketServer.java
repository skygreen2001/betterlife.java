package com.zyp.bb.util.socket.server;

import com.zyp.bb.respository.BbUserRepository;
import com.zyp.bb.service.MsgHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Socket服务器
 *
 * @author skygreen2001
 * @date 2019/06/03
 */
@Service
public class SocketServer {
    @Autowired
    private BbUserRepository bbUserRepository;
    @Autowired
    private MsgHandleService msgService;
    public static final int SERVER_PORT = 9999;
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    public static ConcurrentMap<String, ServerThread> socketUsers = new ConcurrentHashMap<String, ServerThread>();

    /**
     * 查看是否存在该socket用户
     *
     * @param accessToken 用户身份句柄
     */
    public static boolean isSocketUser(String accessToken) {
        if (socketUsers.keySet().contains(accessToken)) {
            return true;
        }
        return false;
    }

    /**
     * 发送socket用户消息
     *
     * @param accessToken 用户身份句柄
     * @param message     消息
     */
    public static void sendSocketMsg(String accessToken, String message) {
        if (socketUsers.keySet().contains(accessToken)) {
            ServerThread ust = socketUsers.get(accessToken);
            ust.sendMsgToClient(message);
        }
    }

    public void run() {
        try {
            //服务端在20006端口监听客户端请求的TCP连接
            ServerSocket server = new ServerSocket(SERVER_PORT);
            logger.debug("服务端已经启动，等待客户端连接");
            Socket client;
            boolean f = true;
            while (f) {
                //等待客户端的连接，如果没有获取连接
                client = server.accept();
                logger.debug("与客户端连接成功！");

                //为每个客户端连接开启一个线程
                ServerThread userSocketThread = new ServerThread(client, bbUserRepository, msgService);
                new Thread(userSocketThread).start();
            }
            socketUsers.clear();
            server.close();

        } catch (Exception ex) {
            socketUsers.clear();
            ex.printStackTrace();
        }
    }

}