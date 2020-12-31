package com.mashirro.xinfang_admin.controller.common;


import com.mashirro.xinfang_common.pojo.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController {

    /**
     * 用户登录
     *
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
        } catch (LockedAccountException e2) {
            e2.printStackTrace();
            return Result.error("账户被锁定!");
        } catch (IncorrectCredentialsException e3) {
            e3.printStackTrace();
            return Result.error("密码不正确!");
        } catch (AuthenticationException e4) {
            e4.printStackTrace();
            return Result.error("登陆出现异常,请联系管理员!");
        }
    }
}
