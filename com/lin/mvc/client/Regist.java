package com.lin.mvc.client;

import com.lin.mvc.domain.User;
import com.lin.mvc.service.Register;

import java.util.Scanner;

/**
 * Created by K Lin on 2017/7/16.
 */
public class Regist {
    public static void regist() {
        String account = null;
        String password = null;
        String name = null;
        String context = null;
        System.out.println("欢迎来到注册页面");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入注册账号（账号长度不能超过15个字符）");
            while (true) {
                context = sc.nextLine();
                if (context.trim().isEmpty()) {
                    System.out.println("注册账户不能为空，请重新输入");
                } else if (context.trim().length() > 15) {
                    System.out.println("注册账户超长，请重新输入");
                } else {
                    account = context.trim();
                    break;
                }
            }
            System.out.println("请输入账户密码（密码长度不能超过15个字符）");
            while (true) {
                context = sc.nextLine();
                if (context.trim().isEmpty()) {
                    System.out.println("所设密码不能为空，请重新输入");
                } else if (context.trim().length() > 15) {
                    System.out.println("密码超长，请重新输入");
                } else {
                    password = context.trim();
                    break;
                }
            }
            System.out.println("请输入用户名（用户名不能超过10个字符）");
            while (true) {
                context = sc.nextLine();
                if (context.trim().isEmpty()) {
                    System.out.println("用户名不能为空，请重新输入");
                } else if (context.trim().length() > 10) {
                    System.out.println("用户名超长，请重新输入");
                } else {
                    name = context.trim();
                    break;
                }
            }
            User user = new User();
            user.setAccount(account);
            user.setUsername(name);
            user.setPassword(password);//将信息写入user中
            if (Register.register(user)) {
                System.out.println("注册新账号成功。可以进行登录。");
                Login.login();
                break;
            } else {
                System.out.println("该账号已存在，请重新注册");
            }
        }
    }
}
