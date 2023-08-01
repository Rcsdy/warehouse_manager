package com.pn.service.impl;

import com.pn.dto.AssignRoleDto;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.mapper.RoleMapper;
import com.pn.page.Page;
import com.pn.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//@CacheConfig(cacheNames = "com.pn.service.impl.RoleServiceImpl")
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    //    @Cacheable(key = "'all:role'")
    @Override
    public List<Role> queryAllRole() {
        return roleMapper.findAllRole();
    }

    @Override
    public List<Role> queryAllRoleByUID(Integer userId) {

        List<Role> roleList = roleMapper.findRolesByUserId(userId);

        return roleList;
    }

    @Override
    public Result insertRoleByRoleID(AssignRoleDto assignRoleDto) {
        List<Integer> roleIdList = new ArrayList<>();

        for (String roleName : assignRoleDto.getRoleCheckList()) {
            Integer roleId = roleMapper.findRoleIdByName(roleName);
            roleIdList.add(roleId);
        }

        Integer integer = 0;
        Integer i = roleMapper.removeRoleByUserId(assignRoleDto.getUserId());

        for (Integer roleId : roleIdList) {
            integer = roleMapper.insertRoleByRoleId(roleId, assignRoleDto.getUserId());
        }

        if (integer > 0) return Result.ok("分配成功");

        return Result.err(Result.CODE_ERR_BUSINESS, "分配失败");
    }

    @Override
    public Page queryRoleByPage(Page page, Role role) {
        Integer count = roleMapper.findRoleRowCount(role);

        List<Role> roleList = roleMapper.findRoleByPage(page, role);

        page.setTotalNum(count);
        page.setResultList(roleList);
        return page;
    }

//    @CacheEvict(key = "'all:role'")
    @Override
    public Result saveRole(Role role) {
        Role role1 = roleMapper.findRoleByNameOrCode(role.getRoleName(), role.getRoleCode());
        if(role1 != null) return Result.err(Result.CODE_ERR_BUSINESS,"角色已存在");

        int i = roleMapper.insertRole(role);
        if (i > 0) return Result.ok("创建成功");

        return Result.err(Result.CODE_ERR_BUSINESS, "创建失败");
    }

//    @CacheEvict(key = "'all:role'")
    @Override
    public Result updateRoleState(Role role) {

        int i = roleMapper.updateRoleStateByRoleId(role.getRoleId(), role.getRoleState());
        if (i > 0) return Result.ok("更改成功");

        return Result.err(Result.CODE_ERR_BUSINESS, "更改失败");
    }

//    @CacheEvict(key = "'all:role'")
    @Transactional
    @Override
    public Result deleteRole(Integer roleId) {

        Integer integer = roleMapper.removeRoleByRoleId(roleId);
        int i = roleMapper.removeRoleAuthByRoleId(roleId);
        if (integer > 0) return Result.ok("删除成功");

        return Result.err(Result.CODE_ERR_BUSINESS, "删除失败");
    }

//    @CacheEvict(key = "'all:role'")
    @Override
    public Result setRoleByRoleId(Role role) {
        int i = roleMapper.updateRoleDescByRoleId(role);

        if (i > 0) return Result.ok("修改成功");

        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败");
    }
}
