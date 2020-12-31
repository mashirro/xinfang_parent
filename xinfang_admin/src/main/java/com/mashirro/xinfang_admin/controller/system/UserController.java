package com.mashirro.xinfang_admin.controller.system;


import com.mashirro.xinfang_common.pojo.Result;
import com.mashirro.xinfang_system.entity.User;
import com.mashirro.xinfang_system.service.UserService;
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

    /**
     * 条件查询用户列表
     *
     * @param user
     * @return
     */
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
}
