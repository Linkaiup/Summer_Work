package com.lin.mvc.service;

import com.lin.mvc.daoImpl.AccountDaoImpl;
import com.lin.mvc.domain.User;

/**
 * Created by K Lin on 2017/7/16.
 */
public class ChackLogin {

    public static boolean chack(User user)
    {
        AccountDaoImpl accountDaoImpl=new AccountDaoImpl();
        return accountDaoImpl.isUserExisted(user);
    }
}
