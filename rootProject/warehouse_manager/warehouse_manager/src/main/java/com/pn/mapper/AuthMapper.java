package com.pn.mapper;

import com.pn.entity.Auth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthMapper {
    //根据userID查询菜单
    public List<Auth> findAuthByUid(Integer userId);

    public List<Auth> findAllAuth();

    public List<Integer> findAuthByRoleId(Integer roleId);

    public int deleteAuthByRoleId(Integer roleId);

    public int insertAuthByRoleId(Integer authId, Integer roleId);
}
