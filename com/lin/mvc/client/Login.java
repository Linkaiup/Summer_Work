package com.lin.mvc.client;

import com.lin.mvc.domain.User;
import com.lin.mvc.service.ChackLogin;

import java.util.Scanner;

/**
 * Created by K Lin on 2017/7/16.
 */
public class Login {
    public static void login() {
        String account = null;
        String password = null;
        Scanner sc = new Scanner(System.in);
        String context = null;
        while (true) {


            System.out.println("请输入你的账号：");
            while (true) {
                context = sc.nextLine();
                if (context.trim().isEmpty()) {
                    System.out.println("输账户不能为空，请重新输入");
                } else if (context.trim().length() > 15) {
                    System.out.println("账户超长，请重新输入");
                } else {
                    account = context.trim();
                    break;
                }
            }
            System.out.println("请输入你的密码");
            while (true) {
                context = sc.nextLine();
                if (context.trim().isEmpty()) {
                    System.out.println("密码不能为空，请重新输入");
                } else if (context.trim().length() > 15) {
                    System.out.println("密码超长，请重新输入");
                } else {
                    password = context.trim();
                    break;
                }
            }
            User user = new User();
            user.setAccount(account);
            user.setPassword(password);//将信息写入user中
            ChackLogin chackLogin = new ChackLogin();
            if (chackLogin.chack(user))//如果信息正确，返回true，并返回用户名
            {
                ChatClient chatClient = new ChatClient();
                chatClient.init(user);
                chatClient.readAndSend();
                break;
            } else {
                System.out.println("账号密码不符，请重新登录");
            }
        }

    }
}
