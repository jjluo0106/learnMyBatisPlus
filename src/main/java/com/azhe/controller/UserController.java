package com.azhe.controller;


import cn.hutool.core.bean.BeanUtil;
import com.azhe.domain.dto.UserFormDTO;
import com.azhe.domain.pojo.User;
import com.azhe.server.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "用戶管理街口")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;



    @ApiOperation("新增用戶接口")
    @PostMapping("/insertUser")
    public void saveUser(@RequestBody UserFormDTO userFormDTO){
        //1.把DTO拷貝到PO
        User user = BeanUtil.copyProperties(userFormDTO, User.class);
        //2.新增
        userService.save(user);
    }


    @ApiOperation("刪除用戶接口")
    @DeleteMapping("/deleteUser")
    public void deleteUser(@ApiParam("用戶id") @PathVariable("id") Long id){

        userService.removeById(id);
    }


    @ApiOperation("根據ID查詢用戶接口")
    @DeleteMapping("/queryUsers")
    public UserFormDTO queryUsers(@ApiParam("用戶id") @PathVariable("id") Long id){

        User user = userService.getById(id);
        return BeanUtil.copyProperties(user, UserFormDTO.class);
    }



    @ApiOperation("根據ID查詢用戶接口")
    @PostMapping ("/queryUserByIds")
    public List<UserFormDTO> queryUserByIds(@ApiParam("用戶id") @RequestParam("id") List<Long> id){

        List<User> users = userService.listByIds(id);

        return BeanUtil.copyToList(users, UserFormDTO.class); // 糊塗包-不同對象的相同參數複製
    }


    @ApiOperation("查詢所有用戶接口")
    @GetMapping("/queryAllUser")
    public List<User> queryAllUser(){

        return userService.list();
    }
}
