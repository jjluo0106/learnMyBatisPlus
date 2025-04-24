package com.azhe.server;

import com.azhe.domain.query.UserQuery;
import com.azhe.mapper.UserMapper;
import com.azhe.domain.pojo.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServer extends ServiceImpl<UserMapper, User> implements IUserService {


    // 模糊查詢
    public List<User> queryUsers(UserQuery userQuery){
        return lambdaQuery().
//                like(User::getName, userQuery.getName()).
        like( userQuery.getName() != null, User::getName, userQuery.getName()). // 如果name有值才做模糊查詢
        between(userQuery.getMinAge() != null, User::getAge, userQuery.getMinAge(), userQuery.getMaxAge()).
                list();
    }

    // 測試查詢
    public List<User> queryUsers2(){
        return Db.lambdaQuery(User.class).eq(User::getAge, 20).list();
    }
}
