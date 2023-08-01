package com.pn.service.impl;

import com.pn.entity.Result;
import com.pn.entity.User;
import com.pn.mapper.UserMapper;
import com.pn.page.Page;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User queryUserByCode(String userCode) {
        return userMapper.findUserByCode(userCode);
    }

    @Override
    public Page queryUserByPage(Page page, User user) {

        Integer count = userMapper.findRowCount(user);
        List<User> userList = userMapper.findUserByPage(page, user);

        page.setTotalNum(count);
        page.setResultList(userList);

        return page;
    }

    @Override
    public Result addUser(User user) {

        User user1 = userMapper.findUserByCode(user.getUserCode());
        if(user1 != null) return Result.err(Result.CODE_ERR_BUSINESS,"账号已存在");

        String password = DigestUtil.hmacSign(user.getUserPwd());
        user.setUserPwd(password);

        int i = userMapper.insertUser(user);
        if(i > 0) return Result.ok("用户添加成功");
        return Result.err(Result.CODE_ERR_BUSINESS,"用户添加失败");
    }

    @Override
    public Result updateState(User user) {
        int i = userMapper.updateState(user.getUserId(), user.getUserState());

        if(i > 0) return Result.ok("更改成功");

        return Result.err(Result.CODE_ERR_BUSINESS,"更改失败");
    }

    @Override
    public Result deleteUserByUid(List<Integer> userIdList) {

        int i = userMapper.serIsDeleteByUid(userIdList);
        if(i > 0) return Result.ok("删除成功");

        return Result.err(Result.CODE_ERR_BUSINESS,"删除失败");
    }

    @Override
    public Result updateUserNameByUid(User user) {
        int i = userMapper.updateUserNameByUid(user);
        if(i > 0) return Result.ok("修改成功");

        return Result.err(Result.CODE_ERR_BUSINESS,"修改失败");
    }

    @Override
    public Result updateUserPwd(Integer userId) {

        String pwd = DigestUtil.hmacSign("123456");
        int i = userMapper.updateUserPwd(userId, pwd);
        if(i > 0) return Result.ok("重置成功");

        return Result.err(Result.CODE_ERR_BUSINESS,"重置失败");
    }


}
