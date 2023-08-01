package com.pn.mapper;

import com.pn.entity.User;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

/*
* user_info表对于sql
* */
@Mapper
public interface UserMapper {
    //    根据账号查找用户信息
    public User findUserByCode(String userCode);

    public Integer findRowCount(User user);

    public List<User> findUserByPage(@Param("page") Page page,@Param("user") User user);

    public int insertUser(User user);

    public int updateState(Integer userId,String userState);

    public int serIsDeleteByUid(List<Integer> userIdList);

    public int updateUserNameByUid(User user);

    public int updateUserPwd(Integer userId, String userPwd);
}
