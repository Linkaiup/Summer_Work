package com.lin.mvc.client;

import com.lin.mvc.service.ChatServer;
import com.mysql.fabric.Server;

import java.util.Scanner;

/**
 * Created by K Lin on 2017/7/16.
 */
public class Index {

    public static void main(String[] args) {

        System.out.println("欢迎来到link聊天室。");
        System.out.println("登录请按1，注册请按2");
        Scanner sc = new Scanner(System.in);
        while (true) {
            switch (sc.next()) {
                case ("1"): {
                    Login.login();
                    break;
                }
                case ("2"): {
                    Regist.regist();
                    break;
                }
                default:
                    System.out.println("输入有误，请重新选择");
            }
        }
    }
}
