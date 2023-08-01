package com.pn.service;

import com.pn.dto.AuthGrantDto;
import com.pn.entity.Auth;
import com.pn.entity.Result;

import java.util.List;

public interface AuthService {
    public List<Auth> authTreeByUid(Integer userId);

    public List<Auth> allAuthTree();

    public List<Integer> queryAuthByRoleId(Integer roleId);

    public Result insertAuthByRoleId(AuthGrantDto authGrantDto);
}
