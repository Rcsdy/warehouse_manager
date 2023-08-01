package com.pn.service;

import com.pn.dto.AssignRoleDto;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.page.Page;

import java.util.List;

public interface RoleService {

    public List<Role> queryAllRole();

    public List<Role> queryAllRoleByUID(Integer userId);

    public Result insertRoleByRoleID(AssignRoleDto assignRoleDto);

    public Page queryRoleByPage(Page page, Role role);

    public Result saveRole(Role role);

    public Result updateRoleState(Role role);

    public Result deleteRole(Integer roleId);

    public Result setRoleByRoleId(Role role);
}
