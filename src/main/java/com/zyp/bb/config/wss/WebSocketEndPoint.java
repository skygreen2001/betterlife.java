package com.zyp.bb.config.wss;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 搜索关键词: 微信小程序 websocket java
 * Spring Boot 系列 - WebSocket 简单使用: https://www.jianshu.com/p/161df01cc8af
 * 微信小程序第五弹 websocket使用 +java后台代码: https://www.jianshu.com/p/9d91a560c459
 * springboot集成websocket实现: http://www.manongjc.com/detail/6-quhmitfrltnogpt.html
 * java使用websocket，并且获取HttpSession: https://www.cnblogs.com/zhuxiaojie/p/6238826.html
 *
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @author skygreen2001
 */
// 搭建微信小程序服务: https://zhuanlan.zhihu.com/p/28057698
// 微信小程序访问服务端类似路径: wss://wss.bb.com/wss/bbwx/websocket (域名路径: wss://wss.bb.com/wss/, 端口: 443)
@ServerEndpoint(value="/bbwx/websocket", configurator = WebsocketConfigurator.class)
@Component
public class WebSocketEndPoint {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketEndPoint> webSocketSet = new CopyOnWriteArraySet<WebSocketEndPoint>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 用户标识
    private Long userId;

    // 用户 Access Token
    private String at;
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        this.session = session;
        this.userId = Long.parseLong(config.getUserProperties().get("userId").toString());
        this.at = config.getUserProperties().get("at").toString();
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        try{
            sendMessage("进入消息队列");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("来自客户端的消息:" + message);
        //群发消息
        for(WebSocketEndPoint item: webSocketSet){
            try {
                if(message.equals("你好")){
                    item.sendMessage(" 回复的消息:"+"你好，请问？");
                    continue;
                }
//                item.sendMessage(message);
                item.sendMessage(" 回复的消息:"+message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
//        this.session.getBasicRemote().sendText(message);//阻塞式发送消息
        this.session.getAsyncRemote().sendText(message);//非阻塞制发送消息
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketEndPoint.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketEndPoint.onlineCount--;
    }
}