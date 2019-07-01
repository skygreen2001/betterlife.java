package com.zyp.bb.util.socket.client;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


/**
 * Java Socket接收和发送（字符串）: https://blog.csdn.net/c929833623lvcha/article/details/12200491
 */
public class LoginClient {
    public static final String SERVER_HOST = "192.168.3.100";
    public static final int SERVER_PORT = 9999;

    public static final int SECOND_TIME = 10;
    public static final String USER_TOKEN = "$2a$10$uHSB/69GfS0ZwHA5fKzzw.cVgroH0qcaid7H5skIBA/OLgAXN4ABG";
    public static final String HEART_MSG = "1";

    private static final Logger logger = LoggerFactory.getLogger(LoginClient.class);
    private static Socket socket;

    public static void main(String[] args) {
        startClientSocket();
//        consoleSendMsgs();
        timerSendMsg();
    }

    /**
     * 启动client socket服务
     */
    private static void startClientSocket() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 第一次发送用户信息后，定时发送信息
     */
    private static void timerSendMsg() {

        try {
            boolean play = true;
            // 服务端输出
            PrintStream out = new PrintStream(socket.getOutputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));


            out.println(USER_TOKEN);
            logger.debug("用户登录:" + USER_TOKEN);


            char[] buffer = new char[1024];
            int index = br.read(buffer, 0, 1024);
            String reply=new String(buffer, 0, index);
//            String reply=br.readLine();
            JSONObject replyJ = new JSONObject(reply);
            if (replyJ.get("result").equals("1")) {
                while (play) {
                    out.println(HEART_MSG);
                    buffer = new char[1024];
                    index = br.read(buffer, 0, 1024);
                    if (index<=0) {
                        continue;
                    }
                    reply=new String(buffer, 0, index);
//                    reply = br.readLine();
                    logger.debug("接收服务器的信息：" + reply);
                    Thread.sleep(SECOND_TIME * 1000);
                }
            }
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 控制台输入进行Socket通信
     */
    private static void consoleSendMsgs() {
        try {
            boolean play = true;
            // 控制台输入
            Scanner inScan = new Scanner(System.in);
            inScan.useDelimiter("\n");

            // 服务端输出
            Scanner serverScanner = new Scanner(socket.getInputStream());
            serverScanner.useDelimiter("\n");
            PrintStream out = new PrintStream(socket.getOutputStream());

            while (play) {
                System.out.println("请输入：");
                String input = inScan.next().trim();
                out.println(input);
                logger.debug(input);

                if (serverScanner.hasNext()) {
                    String str = serverScanner.next();
                    logger.debug(str);
                }
                if ("bye".equals(input)) {
                    play = false;
                }
            }
            inScan.close();
            serverScanner.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}