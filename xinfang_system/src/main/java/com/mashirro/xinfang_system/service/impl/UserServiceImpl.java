package com.mashirro.xinfang_system.service.impl;


import com.mashirro.xinfang_system.dao.UserMapper;
import com.mashirro.xinfang_system.entity.User;
import com.mashirro.xinfang_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> getUsers(User user) {
        return userMapper.selectUsers(user);
    }

    @Override
    public User findUserByLoginAccount(String loginAccount) {
        return userMapper.findUserByLoginAccount(loginAccount);
    }
}
