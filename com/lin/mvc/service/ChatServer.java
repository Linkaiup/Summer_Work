package com.lin.mvc.service;

import com.lin.mvc.util.UserMap;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by K Lin on 2017/7/16.
 */
public class ChatServer {
    private static final int SERVER_PORT=30000;
    public static UserMap<String,PrintStream> clients=new UserMap<>();
    public void init()
    {
        try (
                ServerSocket ss=new ServerSocket(SERVER_PORT))
        {
            while(true)
            {
                System.out.println("服务器已经开启");
                Socket socket=ss.accept();//服务器开启入口，如果有socket访问则开启一个线程处理
                new ChatServerThread(socket).start();
            }
        } catch (IOException e) {
            System.out.println("服务器启动失败，是否端口"+SERVER_PORT+"已被占用？");
        }
    }
}
