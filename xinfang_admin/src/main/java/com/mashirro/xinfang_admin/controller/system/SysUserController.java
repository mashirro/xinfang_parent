package com.mashirro.xinfang_admin.controller.system;


import com.mashirro.xinfang_common.pojo.Result;
import com.mashirro.xinfang_framework.entity.SysUser;
import com.mashirro.xinfang_framework.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 条件查询用户列表
     *
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Result getUsers(@RequestBody SysUser sysUser) {
        try {
            List<SysUser> sysUsers = sysUserService.getUsers(sysUser);
            return Result.success("查询用户列表成功", sysUsers);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询用户列表失败");
        }
    }
}
