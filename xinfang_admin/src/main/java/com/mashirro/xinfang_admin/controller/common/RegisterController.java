package com.mashirro.xinfang_admin.controller.common;


import com.mashirro.xinfang_common.pojo.Constants;
import com.mashirro.xinfang_common.pojo.Result;
import com.mashirro.xinfang_framework.shiro.utils.PasswordUtil;
import com.mashirro.xinfang_system.entity.User;
import com.mashirro.xinfang_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.UUID;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
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
            String salt = PasswordUtil.generateSalt();
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
}
