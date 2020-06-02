package com.mashirro.xinfang_system.service;

import com.mashirro.xinfang_system.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUsers(User user);

    User findUserByLoginAccount(String loginAccount);

    String register(User user);
}
