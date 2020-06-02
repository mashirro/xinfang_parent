package com.mashirro.xinfang_system.controller;


import com.mashirro.xinfang_common.pojo.Constants;
import com.mashirro.xinfang_common.pojo.Result;
import com.mashirro.xinfang_common.util.shiro.PasswordUtil;
import com.mashirro.xinfang_system.entity.User;
import com.mashirro.xinfang_system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //我们将用一个随机数发生器来产生盐,这比使用用户名作为salt或者根本没有salt要安全得多。
    private static final SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();


    /**
     * 条件查询用户列表
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

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result register(@RequestBody User user) {
        try {
            user.setId(UUID.randomUUID().toString());
            user.setIsDeleted(Constants.ZERO_NUMBER);
            user.setIsLocked(Constants.ZERO_NUMBER);

            //我们将用一个随机数发生器来产生盐,这比使用用户名作为salt或者根本没有salt要安全得多。toHex:返回基础包装字节数组的十六进制格式的String表示形式。
            String salt = generator.nextBytes().toHex();
            user.setSalt(salt);
            user.setPassword(PasswordUtil.getHashedPassword(user.getPassword(), salt));

            String message = userService.register(user);
            if (message != null) {
                return Result.error(message);
            }
            return Result.success("用户注册成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("注册失败");
        }
    }

    /**
     * 用户登录
     * @param loginAccount
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(String loginAccount, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginAccount, password);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            return Result.success("登陆成功", null);
        } catch (UnknownAccountException e1) {
            e1.printStackTrace();
            return Result.error("账户不存在!");
        } catch (LockedAccountException e2){
            e2.printStackTrace();
            return Result.error("账户被锁定!");
        } catch (IncorrectCredentialsException e3){
            e3.printStackTrace();
            return Result.error("密码不正确!");
        } catch (AuthenticationException e4){
            e4.printStackTrace();
            return Result.error("登陆出现异常,请联系管理员!");
        }
    }

}
