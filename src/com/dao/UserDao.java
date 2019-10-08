package com.dao;

import com.domain.User;

import java.util.List;

public interface UserDao {
    List<User> showUser();
    String getUserByName(String username);
    int addUserInfo(User user);
    int editUserInfo(User user);
    int editUserpwd(User user,String password);
    int delUserInfo(int id);
}
