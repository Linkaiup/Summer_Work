package com.lin.mvc.dao;

import com.lin.mvc.domain.User;

/**
 * Created by K Lin on 2017/7/16.
 */
public interface AccountDao {
    public boolean isUserExisted(User user);
    public boolean setAccount(User user);

}
