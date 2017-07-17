package com.lin.mvc.daoImpl;

import com.lin.mvc.dao.AccountDao;
import com.lin.mvc.dbc.DataBaseConnection;
import com.lin.mvc.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by K Lin on 2017/7/16.
 */
public class AccountDaoImpl implements AccountDao{
    public boolean isUserExisted(User user) {//验证登录
        boolean existed = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = DataBaseConnection.getCon();
        try {

            stmt = conn.prepareStatement("SELECT name FROM login WHERE userid=? and password=?");

            stmt.setString(1, user.getAccount());
            stmt.setString(2, user.getPassword());
            rs = stmt.executeQuery();//取得查询结果
            if (rs.next()) {
                user.setUsername(rs.getString("name"));//取得姓名
                existed = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
            DataBaseConnection.free(rs, stmt, conn);
        }


        return existed;
    }
    public boolean setAccount (User user)//注册账户
    {
        boolean ok=false;
        PreparedStatement stmt=null;
        ResultSet rs=null;
        Connection conn=DataBaseConnection.getCon();
        try{
            stmt=conn.prepareStatement("INSERT INTO login(userid,name,password) VALUES(?,?,?)");
            stmt.setString(1,user.getAccount());
            stmt.setString(2,user.getUsername());
            stmt.setString(3,user.getPassword());
            stmt.executeUpdate();
            stmt.clearParameters();

            ok=true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }finally{
            DataBaseConnection.free(rs, stmt, conn);
        }
        return ok;
    }

}
