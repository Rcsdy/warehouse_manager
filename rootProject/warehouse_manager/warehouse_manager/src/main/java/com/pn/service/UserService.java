package com.pn.service;

import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.page.Page;

import java.util.List;

/*
* user_info 表对应serve
* */
public interface UserService {
    public User queryUserByCode(String UserCode);

    public Page queryUserByPage(Page page, User user);

    public Result addUser(User user);

    public Result updateState(User user);

    public Result deleteUserByUid(List<Integer> userIdList);

    public Result updateUserNameByUid(User user);

    public Result updateUserPwd(Integer userId);
}
