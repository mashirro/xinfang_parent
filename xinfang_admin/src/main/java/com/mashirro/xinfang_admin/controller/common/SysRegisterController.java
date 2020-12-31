package com.mashirro.xinfang_admin.controller.common;


import com.mashirro.xinfang_common.pojo.Constants;
import com.mashirro.xinfang_common.pojo.Result;
import com.mashirro.xinfang_framework.shiro.utils.PasswordUtil;
import com.mashirro.xinfang_framework.entity.SysUser;
import com.mashirro.xinfang_framework.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.UUID;

@Controller
public class SysRegisterController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户注册
     *
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result register(@RequestBody SysUser sysUser) {
        try {
            sysUser.setId(UUID.randomUUID().toString());
            sysUser.setIsDeleted(Constants.ZERO_NUMBER);
            sysUser.setIsLocked(Constants.ZERO_NUMBER);
            String salt = PasswordUtil.generateSalt();
            sysUser.setSalt(salt);
            sysUser.setPassword(PasswordUtil.getHashedPassword(sysUser.getPassword(), salt));
            String message = sysUserService.register(sysUser);
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
