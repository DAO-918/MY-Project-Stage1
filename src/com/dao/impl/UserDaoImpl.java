package com.dao.impl;

import com.dao.UserDao;
import com.domain.User;
import com.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public List<User> showUser() {
        return null;
    }

    @Override
    public String getUserByName(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        String sql = "SELECT * FROM user WHERE u_username=? AND is_delete='0'";
        String password = null;
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            res = pstmt.executeQuery();
            while (res.next()){
                password = res.getString("u_password");
            }
            System.out.println("DAOIMPL--获取密码:--"+password+"--对应的用户:--"+username);
            return password;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(res,pstmt,conn);
        }
        return null;
    }

    @Override
    public int addUserInfo(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO user VALUES(0,?,?,?,?,?,?,?,'0')";//是values
        System.out.println("即将注册的用户信息：--"+user);
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user.getU_username());
            pstmt.setString(2,user.getU_password());
            pstmt.setString(3,user.getU_sex());
            pstmt.setString(4,user.getU_hobbies());
            pstmt.setString(5,user.getU_phone());
            pstmt.setString(6,user.getU_email());
            pstmt.setString(7,user.getU_address());
            int result = pstmt.executeUpdate();
            System.out.println("DAOIMPL--添加用户-受影响的行数："+result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(null,pstmt,conn);
        }
        return -1;
    }

    @Override
    public int editUserInfo(User user) {

        return 0;
    }

    @Override
    public int editUserpwd(User user, String password) {
        return 0;
    }

    @Override
    public int delUserInfo(int id) {
        return 0;
    }
}
