package com.mashirro.xinfang_framework.service.impl;


import com.mashirro.xinfang_framework.entity.SysUser;
import com.mashirro.xinfang_framework.dao.SysUserMapper;
import com.mashirro.xinfang_framework.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getUsers(SysUser sysUser) {
        return sysUserMapper.selectUsers(sysUser);
    }

    @Override
    public SysUser findUserByLoginAccount(String loginAccount) {
        return sysUserMapper.findUserByLoginAccount(loginAccount);
    }

    @Override
    public String register(SysUser sysUser) {
        SysUser u = sysUserMapper.findUserByLoginAccount(sysUser.getLoginAccount());
        if (u != null) {
            return "登陆账号不能重复!";
        }
        sysUserMapper.insertSelective(sysUser);
        return null;
    }
}
