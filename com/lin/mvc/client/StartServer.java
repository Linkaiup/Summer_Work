package com.lin.mvc.client;

import com.lin.mvc.service.ChatServer;

/**
 * Created by K Lin on 2017/7/16.
 */
public class StartServer {
    public static void main(String[] args) {
        ChatServer chatServer=new ChatServer();
        chatServer.init();
    }
}
