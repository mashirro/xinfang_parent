package com.mashirro.xinfang_system.controller;


import com.mashirro.xinfang_common.pojo.Result;
import com.mashirro.xinfang_system.entity.User;
import com.mashirro.xinfang_system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Result getUsers(@RequestBody User user) {
        try {
            List<User> users = userService.getUsers(user);
            return Result.success("查询用户列表成功", users);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询用户列表失败");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(String loginAccount, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginAccount, password);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            return Result.success("登陆成功", null);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Result.error("登陆失败");
        }
    }
}
