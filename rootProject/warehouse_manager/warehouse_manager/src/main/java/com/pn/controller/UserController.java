package com.pn.controller;


import com.pn.dto.AssignRoleDto;
import com.pn.entity.CurrentUser;
import com.pn.entity.Result;
import com.pn.entity.Role;
import com.pn.entity.User;
import com.pn.page.Page;
import com.pn.service.RoleService;
import com.pn.service.UserService;
import com.pn.utils.DigestUtil;
import com.pn.utils.TokenUtils;
import com.pn.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user-list")
    public Result userList(Page page, User user) {
        page = userService.queryUserByPage(page, user);
        return Result.ok(page);
    }

    @Autowired
    private TokenUtils tokenUtils;

    @RequestMapping("/addUser")
    public Result addUser(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int createBy = currentUser.getUserId();
        user.setCreateBy(createBy);

        Result result = userService.addUser(user);

        return result;
    }

    @RequestMapping("/updateState")
    public Result updateState(@RequestBody User user) {

        Result result = userService.updateState(user);

        return result;
    }

    @Autowired
    private RoleService roleService;

    @RequestMapping("/user-role-list/{userId}")
    public Result userRoleList(@PathVariable Integer userId) {

        List<Role> roleList = roleService.queryAllRoleByUID(userId);
        return Result.ok(roleList);
    }

    @RequestMapping("/assignRole")
    public Result assingRole(@RequestBody AssignRoleDto assignRoleDto) {
        Result result = roleService.insertRoleByRoleID(assignRoleDto);

        return result;

    }

    //    /user/deleteUser/33"
    @RequestMapping("/deleteUser/{userId}")
    public Result deleteUser(@PathVariable Integer userId) {
        List<Integer> userIdList = new ArrayList<>();
        userIdList.add(userId);

        Result result = userService.deleteUserByUid(userIdList);
        return result;
    }

    //    /user/deleteUserList"
    @RequestMapping("/deleteUserList")
    public Result deleteUser(@RequestBody List<Integer> userIdList) {
        Result result = userService.deleteUserByUid(userIdList);
        return result;
    }

    @RequestMapping("/updateUser")
    public Result updateUser(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        user.setUpdateBy(currentUser.getUserId());

        Result result = userService.updateUserNameByUid(user);

        return result;
    }

//    /user/updatePwd/33
    @RequestMapping("/updatePwd/{userId}")
    public Result updatePwd(@PathVariable Integer userId){
        Result result = userService.updateUserPwd(userId);

        return result;
    }
}
