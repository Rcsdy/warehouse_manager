package com.pn.service.impl;

import com.alibaba.fastjson.JSON;
import com.pn.dto.AuthGrantDto;
import com.pn.entity.Auth;
import com.pn.entity.Result;
import com.pn.mapper.AuthMapper;
import com.pn.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

//@Cacheable(cacheNames = "com.pn.service.impl.AuthServiceImpl")
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public List<Auth> authTreeByUid(Integer userId) {
        String authTreeJson = stringRedisTemplate.opsForValue().get("authTree:" + userId);
        if(StringUtils.hasText(authTreeJson)){
            List<Auth> authTreeList = JSON.parseArray(authTreeJson, Auth.class);
            return authTreeList;
        }

        List<Auth> allAuthList = authMapper.findAuthByUid(userId);
        List<Auth> authTreeList = allAuthToAuthTree(allAuthList,0);
        stringRedisTemplate.opsForValue().set("authTree:" + userId,JSON.toJSONString(authTreeList));
        return authTreeList;
    }

//    @Cacheable(key = "'all:authTree'")
    @Override
    public List<Auth> allAuthTree() {

        List<Auth> allAuth = authMapper.findAllAuth();
        List<Auth> authTree = allAuthToAuthTree(allAuth, 0);

        return authTree;
    }

    @Override
    public List<Integer> queryAuthByRoleId(Integer roleId) {
        List<Integer> authId = authMapper.findAuthByRoleId(roleId);

        return authId;
    }

    @Transactional
    @Override
    public Result insertAuthByRoleId(AuthGrantDto authGrantDto) {
        authMapper.deleteAuthByRoleId(authGrantDto.getRoleId());

        for(Integer authId : authGrantDto.getAuthIds()){
            authMapper.insertAuthByRoleId(authId, authGrantDto.getRoleId());
        }
        return Result.ok("修改成功");
    }

    private List<Auth> allAuthToAuthTree(List<Auth> allAuthList,Integer pid){
        List<Auth> firstLevelAuthList = new ArrayList<>();
        for (Auth auth:allAuthList) {
            if(auth.getParentId() == pid){
                firstLevelAuthList.add(auth);
            }
        }

        for (Auth firstAuth:firstLevelAuthList) {
            List<Auth> secondLevelAuthList = allAuthToAuthTree(allAuthList, firstAuth.getAuthId());
            firstAuth.setChildAuth(secondLevelAuthList);
        }

        return firstLevelAuthList;
    }
}
