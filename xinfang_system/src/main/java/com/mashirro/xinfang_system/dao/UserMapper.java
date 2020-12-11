package com.mashirro.xinfang_system.dao;


import com.mashirro.xinfang_system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;


@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 条件查询用户列表
     * @return
     */
    List<User> selectUsers(User user);

    /**
     * 根据用户账号查询用户
     * @param loginAccount
     * @return
     */
    User findUserByLoginAccount(String loginAccount);
}