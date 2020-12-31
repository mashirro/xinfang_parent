package com.mashirro.xinfang_framework.dao;


import com.mashirro.xinfang_framework.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;


@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 条件查询用户列表
     * @return
     */
    List<SysUser> selectUsers(SysUser sysUser);

    /**
     * 根据用户账号查询用户
     * @param loginAccount
     * @return
     */
    SysUser findUserByLoginAccount(String loginAccount);
}