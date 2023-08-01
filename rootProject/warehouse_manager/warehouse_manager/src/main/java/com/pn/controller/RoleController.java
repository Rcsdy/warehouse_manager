package com.pn.controller;


import com.pn.dto.AuthGrantDto;
import com.pn.entity.CurrentUser;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.page.Page;
import com.pn.service.AuthService;
import com.pn.service.RoleService;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/role-list")
    public Result roleList(){
        List<Role> roleList = roleService.queryAllRole();

        return Result.ok(roleList);
    }

//    /role/role-page-list
    @RequestMapping("/role-page-list")
    public Result rolePageList(Page page, Role role){

        page = roleService.queryRoleByPage(page, role);
        return Result.ok(page);
    }

    @Autowired
    private TokenUtils tokenUtils;

//      /role/role-add
    @RequestMapping("/role-add")
    public Result roleAdd(@RequestBody Role role,@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){

        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();
        role.setCreateBy(createBy);

        Result result = roleService.saveRole(role);

        return result;
    }
//      /role/role-state-update
    @RequestMapping("/role-state-update")
    public Result roleStateUpdate(@RequestBody Role role){
        return roleService.updateRoleState(role);
    }

//    /role/role-delete/15
    @RequestMapping("/role-delete/{roleId}")
    public Result roleDelete(@PathVariable Integer roleId){
        return roleService.deleteRole(roleId);
    }

    @Autowired
    private AuthService authService;

//    /role/role-auth?roleId=1
    @RequestMapping("/role-auth")
    public Result roleAuth(Integer roleId){
        List<Integer> authId = authService.queryAuthByRoleId(roleId);

        return Result.ok(authId);
    }
//    /role/auth-grant
    @RequestMapping("/auth-grant")
    public Result authGrant(@RequestBody AuthGrantDto authGrantDto){

        Result result = authService.insertAuthByRoleId(authGrantDto);
        return result;
    }
//    /role/role-update
    @RequestMapping("/role-update")
    public Result roleUpdate(@RequestBody Role role, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int updateBy = currentUser.getUserId();

        role.setUpdateBy(updateBy);
        Result result = roleService.setRoleByRoleId(role);

        return result;

    }
}
