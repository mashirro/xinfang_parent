package com.mashirro.xinfang_framework.service;

import com.mashirro.xinfang_framework.entity.SysUser;

import java.util.List;

public interface SysUserService {
    List<SysUser> getUsers(SysUser sysUser);

    SysUser findUserByLoginAccount(String loginAccount);

    String register(SysUser sysUser);
}
