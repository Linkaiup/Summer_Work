package com.lin.mvc.service;

import com.lin.mvc.daoImpl.AccountDaoImpl;
import com.lin.mvc.domain.User;

/**
 * Created by K Lin on 2017/7/16.
 */
public class Register {
    public static boolean register(User user) {
        AccountDaoImpl accountDao = new AccountDaoImpl();
        return accountDao.setAccount(user);
    }
}
