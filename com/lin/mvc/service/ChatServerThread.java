package com.lin.mvc.service;

import com.lin.mvc.client.ChatClient;
import com.lin.mvc.util.StatusProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Set;

/**
 * Created by K Lin on 2017/7/16.
 */
public class ChatServerThread extends Thread {
    private Socket socket;
    BufferedReader br = null;
    PrintStream ps = null;

    //接收一个socket来创建ServerThreadSub线程
    public ChatServerThread(Socket s) {
        this.socket = s;
    }

    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ps = new PrintStream(socket.getOutputStream());
            String line = null;
            //当收到socket中传过来的信息时进入循环
            while ((line = br.readLine()) != null) {
                //如果用户登录成功则传来他的用户名
                if (line.startsWith(StatusProtocol.USER_ROUND) && line.endsWith(StatusProtocol.USER_ROUND)) {
                    String userName = getRealMsg(line);
                    //判断该用户是否已经登录
                    if (ChatServer.clients.map.containsKey(userName)) {
                        System.out.println(userName + "尝试重复登录");
                        ps.println(StatusProtocol.NAME_REP);
                    } else {
                        System.out.println(userName + "成功登录");
                        ps.println(StatusProtocol.LOGIN_SUCCESS);
                        ChatServer.clients.put(userName, ps);//因为PrintStream一直存在着，所以可用username与PrintStream
                                                            // 绑定，建立联系
                        for (PrintStream clintPs : ChatServer.clients.valueSet()) {
                            clintPs.println("["+userName+"]已上线。当前在线人数为："+ChatServer.clients.map.size());
                        }                                   //通知所有在线用户有新用户登录
                    }
                }
                //如果读到的以PRIVATE_ROUND开始，并以其结束，则是私聊
                else if (line.startsWith(StatusProtocol.PRIVATE_ROUND) && line.endsWith(StatusProtocol.PRIVATE_ROUND)) {
                    String userAndMsg = getRealMsg(line);
                    //分割字符串，前半段是私聊用户，后半段是聊天消息

                    String user = userAndMsg.split(StatusProtocol.SPLIT_SIGN)[0];
                    String msg = userAndMsg.split(StatusProtocol.SPLIT_SIGN)[1];

                    //获取私聊用户对应的输出流，并发送私聊信息
//                    Set<String> online= (Set<String>)ChatServer.clients.getKey();
//                    String onlineuser="※";
//                    for(String on:online)
//                    {
//                        onlineuser+=on;
//                        onlineuser+="※";
//                    }

                    if(ChatServer.clients.map.containsKey(user)) {
                        ChatServer.clients.map.get(user).println(ChatServer.clients.getKeyByValue(ps) + "对你说：" + msg);
                    }
                    else {
                        ChatServer.clients.map.get(ChatServer.clients.getKeyByValue(ps)).println("该用户不在线，无法进行私聊[无奈]");
                    }
                    //取得对应要发送消息的对象的PrintStream，用PrintStream打印发送的消息
                }
                //有用户想查询在线用户，将在线用户信息返回给该用户
                else if(line.startsWith(StatusProtocol.GET_ONLINE)&&line.startsWith(StatusProtocol.GET_ONLINE))
                {
                    Set<String> online= (Set<String>)ChatServer.clients.getKey();
                    String onlineuser="在线好友有：";
                    for(String on:online)
                    {
                        onlineuser+=on;
                        onlineuser+="  ";
                    }
                    ChatServer.clients.map.get(ChatServer.clients.getKeyByValue(ps)).println(onlineuser);
                }
                //群聊
                else {
                    //去掉备注符号，得到真实消息
                    String msg = getRealMsg(line);
                    for (PrintStream clintPs : ChatServer.clients.valueSet()) {

                        clintPs.println(ChatServer.clients.getKeyByValue(ps) + "说：" + msg);

                    }
                }
            }
            //捕捉到异常后，表明该Socket对应客户端出现问题，所以将其对应的输出流从map中删除
        } catch (IOException e) {
            for (PrintStream clintPs : ChatServer.clients.valueSet()) {
                clintPs.println(ChatServer.clients.getKeyByValue(ps) + "退出聊天室");
                ChatServer.clients.removeByValue(ps);
                clintPs.println("聊天室剩余人数为：" + ChatServer.clients.map.size());
            }
            //关闭网络、IO资源
            try {
                if (br != null) {
                    br.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private String getRealMsg(String line) {
        return line.substring(StatusProtocol.PROTOCAL_LEN, line.length() - StatusProtocol.PROTOCAL_LEN);
    }

}
