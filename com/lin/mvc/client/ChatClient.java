package com.lin.mvc.client;

import com.lin.mvc.domain.User;
import com.lin.mvc.util.StatusProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by K Lin on 2017/7/16.
 */
public class ChatClient {
    private static final int SERVER_PORT=30000;
    private Socket socket;
    private PrintStream ps;
    private BufferedReader br;
    private BufferedReader keyIn;
    public void init(User user)
    {

        //连接到服务器端
        try {
            keyIn=new BufferedReader(new InputStreamReader(System.in));
            socket=new Socket("127.0.0.1",SERVER_PORT);
            ps=new PrintStream(socket.getOutputStream());
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true) {

                ps.println(StatusProtocol.USER_ROUND +user.getUsername()+ StatusProtocol.USER_ROUND);
                //读取服务器响应
                String result = br.readLine();
                if (result.equals(StatusProtocol.NAME_REP)) {
                    System.out.println("你已经登录，请不要尝试重复登录！");
                    System.out.println("重新登录请按1，注册请按2");
                    Login.login();
                }
                if (result.equals(StatusProtocol.LOGIN_SUCCESS)) {
                    System.out.println(user.getUsername()+",你已登录成功。可以开始聊天。私聊请按//:（要私聊的好友名称）。online可"+
                            "用于查看在线好友");
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("找不到远程服务器");
            closeRs();
            System.exit(1);
        }
        catch (IOException e)
        {
            System.out.println("网络异常，重新登录");
            closeRs();
            System.exit(1);
        }
        new ChatCilentThread(br).start();
    }
    public void readAndSend()
    {

        try {
            String line= null;
            while((line=keyIn.readLine())!=null)
            {
                if(line.indexOf(":")>0&&line.startsWith("//"))
                {
                    line=line.substring(2);
                    if(line.endsWith(":")) {
                        System.out.println("聊天信息不能为空，请重新输入");
                    }
                    else
                    {
                        ps.println(StatusProtocol.PRIVATE_ROUND + line.split(":")[0]
                                + StatusProtocol.SPLIT_SIGN + line.split(":")[1] + StatusProtocol.PRIVATE_ROUND);
                    }
                }
                else if(line.contains("online"))
                {
                    ps.println(StatusProtocol.GET_ONLINE+line+StatusProtocol.GET_ONLINE);
                }
                else
                {
                    ps.println(StatusProtocol.MSG_ROUND+line+StatusProtocol.MSG_ROUND);
                }
            }
        }//捕获到异常，关闭网络资源，并退出该程序
        catch (Exception e) {
            System.out.println("网络异常，请重新登录");
            closeRs();
            System.exit(1);
        }
    }
    //关闭Socket等
    private void closeRs()
    {
        try {
            if(keyIn!=null)
            {
                ps.close();
            }
            if(br!=null)
            {
                br.close();
            }
            if(ps!=null)
            {
                ps.close();
            }
            if(socket!=null)
            {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

