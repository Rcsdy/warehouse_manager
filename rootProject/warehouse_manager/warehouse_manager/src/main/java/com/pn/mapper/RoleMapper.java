package com.pn.mapper;

import com.pn.entity.Role;
import com.pn.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    public List<Role> findAllRole();

    //根据用户id查询用户已分配的角色
    public List<Role> findRolesByUserId(Integer userId);

    public Integer findRoleIdByName(String roleName);

    public List<Integer> findRoleIdByUserId(Integer userId);

    public Integer removeRoleByUserId(Integer userId);

    public Integer insertRoleByRoleId(Integer roleId, Integer userId);

    public Integer findRoleRowCount(Role role);

    public List<Role> findRoleByPage(@Param("page") Page page, @Param("role") Role role);

    public Role findRoleByNameOrCode(String roleName,String roleCode);

    public int insertRole(Role role);

    public int updateRoleStateByRoleId(Integer roleId, String roleState);

    public int removeRoleByRoleId(Integer roleId);

    public int removeRoleAuthByRoleId(Integer roleId);

    public int updateRoleDescByRoleId(Role role);
}
