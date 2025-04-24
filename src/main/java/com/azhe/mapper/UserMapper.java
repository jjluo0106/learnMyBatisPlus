package com.azhe.mapper;

import com.azhe.domain.pojo.User;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    void myUpdate(@Param(Constants.WRAPPER) LambdaUpdateWrapper<User> wrapper,@Param("name") String name);

    void insertBatchSomeColumn(ArrayList<User> list);
}
