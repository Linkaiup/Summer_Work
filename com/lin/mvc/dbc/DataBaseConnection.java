package com.lin.mvc.dbc;

/**
 * Created by K Lin on 2017/7/16.
 */
import java.sql.*;

public class DataBaseConnection {
    private static final String jdbcname="com.mysql.jdbc.Driver";
    private static final String	DBURL="jdbc:mysql://localhost:3306/usertable";
    private static final String DBUSER="root";
    private static final String DBPASS="112316";

    public static Connection getCon(){
        try
        {
            Class.forName(jdbcname);//加载驱动
        }catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        Connection conn=null;
        SQLException ex=null;
        try
        {
            conn=DriverManager.getConnection(DBURL,DBUSER,DBPASS);//连接数据库
        }catch(SQLException e)
        {
            System.out.println("fail to connect");
            e.printStackTrace();
        }
        return conn;
    }

    // 释放连接
    public static void free(ResultSet rs, PreparedStatement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close(); // 关闭结果集
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close(); // 关闭Statement
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close(); // 关闭连接
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }

    }


}

