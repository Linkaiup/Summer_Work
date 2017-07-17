package com.lin.mvc.client;

import com.lin.mvc.service.ChatServer;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by K Lin on 2017/7/17.
 */
public class ChatCilentThread extends Thread{

        BufferedReader br=null;
        //使用一个网络输入流来创建客户端线程
        public ChatCilentThread(BufferedReader bufferedReader)
        {
            this.br=bufferedReader;
        }
        public void run()
        {
            try {
                String line=null;
                while((line=br.readLine())!=null)
                {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if(br!=null)
                    {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

}
