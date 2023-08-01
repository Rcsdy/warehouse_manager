package com.pn.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//接受分配角色请求的json数据的dto类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignRoleDto {

    private Integer userId;

    private List<String> roleCheckList;
}
