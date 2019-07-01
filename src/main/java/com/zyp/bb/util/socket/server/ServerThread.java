package com.zyp.bb.util.socket.server;

import com.zyp.bb.respository.BbUserRepository;
import com.zyp.bb.service.MsgHandleService;
import com.zyp.bb.util.UtilsCommon;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashSet;

/**
 * 一个用户开一个Socket进程
 *
 * @author skygreen2001
 * @date 2019/06/03
 */
public class ServerThread implements Runnable {

    private BbUserRepository bbUserDao;

    private MsgHandleService msgService;
    /**
     * 读取不到数据超过36秒，判断为断开连接
     */
    public static final int TIMEOUT_READ = 36;

    public String userToken;

    public Long userId;

//    private ZonedDateTime recordTime;
    private PrintStream out;

    private final Logger logger = LoggerFactory.getLogger(ServerThread.class);
    private Socket client;

    public ServerThread(Socket client, BbUserRepository ittrUserDao, MsgHandleService msgService) {
        this.bbUserDao = ittrUserDao;
        this.msgService = msgService;
        this.client = client;
        try {
            this.client.setSoTimeout(TIMEOUT_READ * 1000);
        } catch (SocketException exception) {
            logger.debug(exception.getMessage());
        }
    }


    /**
     * 发送消息到客户端
     */
    public void sendMsgToClient(String msg) {
        try {
            if (client.isConnected() && !client.isClosed() && !client.isOutputShutdown()) {
                if (out == null) {
                    out = new PrintStream(client.getOutputStream());
                }
                if (out != null) {
                    out.print(msg);
                    logger.debug("主动发送消息到客户端【" + userToken + "】:" + msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String defaultResponse(){
        return jsonResponse("1", "");
    }

    private String jsonResponse(String result, String errorInfo){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("result", result);
            if (!StringUtils.isEmpty(errorInfo)) {
                jsonObject.put("errorInfo", errorInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 协议约定:
     */
    @Override
    public void run() {
        PrintStream out = null;
        try {

            //获取Socket的输出流，用来向客户端发送数据
            out = new PrintStream(client.getOutputStream(), true, "UTF8");

            //获取Socket的输入流，用来接收从客户端发送过来的数据
            BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));

//            recordTime = ZonedDateTime.now();
            boolean flag = true;

            char[] buffer = new char[1024];
            int index = buf.read(buffer, 0, 1024);
            String userInfo = new String(buffer, 0, index);
//            String userToken = buf.readLine();
            if (!StringUtils.isEmpty(userInfo) && UtilsCommon.isJsonString(userInfo)) {
                userInfo = userInfo.replace("\n", "");
                JSONObject jo = new JSONObject(userInfo);
                this.userToken = jo.optString("accessToken");
                if (isBcrptAccessToken(userToken)) {
                    userId =  jo.optLong("userId");
                    if (userId!=null ) {
                        bbUserDao.setOnline(userId, userToken);
                        if (bbUserDao.firstLoginOfflineMsgUsers == null) {
                            bbUserDao.firstLoginOfflineMsgUsers = new HashSet<>();
                        }
                        bbUserDao.firstLoginOfflineMsgUsers.add(userId);
                    }
                    logger.debug("合法的用户请求，用户token信息: " + userToken);
                    //回馈用户合法，可使用Socket服务
                    out.print(defaultResponse());
                    SocketServer.socketUsers.put(userToken, this);

                    String readStr;
                    while (flag) {
                        //接收从客户端发送过来的数据
//                        readStr = buf.readLine();
                        index = buf.read(buffer, 0, 1024);
                        if (index <= 0) {
                            break;
                        }
                        readStr = new String(buffer, 0, index);
                        readStr = readStr.replace("\n", "");

                        if (readStr == null || "".equals(readStr)) {
                            flag = false;
                        } else {
                            if ("bye".equals(readStr)) {
                                flag = false;
                            } else {
                                if (!SocketServer.socketUsers.containsKey(userToken)) {
                                    SocketServer.socketUsers.put(userToken, this);
                                }
                                msgService.recordHeartbeat(userToken);
                                msgService.handleOfflineMsgs(userToken);
                                defaultResponse();
                                //向客户端输出数据
//                                out.println(jsonResponse(new Date() + ",Hello,I'm Server!请确认您说的是:" + readStr, "");
                                //将接收到的字符串前面加上echo，发送到对应的客户端
//                                logger.debug("echo:" + readStr);
                                logger.debug("ECHO[userToken]:" + readStr);
                            }
                        }
                    }

                    SocketServer.socketUsers.remove(userToken);
                    msgService.handleLeave(userToken);
                } else {
                    String errorinfo = "非法的用户请求，用户token信息: " + userToken + "，已断开！";
                    out.print(jsonResponse("500", errorinfo));
                    logger.debug(errorinfo);
                }
            }

            out.close();
            client.close();
            Thread.currentThread().interrupt();


        } catch (SocketTimeoutException ex) {
            //记录读取超时: 客户端不再发送消息
//            recordTime = ZonedDateTime.now();
//            logger.debug(recordTime.toString());
            if (!StringUtils.isEmpty(userToken)) {
                SocketServer.socketUsers.remove(userToken);
            }
            if (out != null) {
                out.close();
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }


    /**
     * 一共60位长度
     * 以前缀开头: $2a$10$
     *
     * @return
     */
    private static boolean isBcrptAccessToken(String userToken) {
        String prefix = "$2a$10$";
        if (!StringUtils.isEmpty(userToken)) {
            userToken = userToken.trim();
            if (userToken.length() == 60 && userToken.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

}