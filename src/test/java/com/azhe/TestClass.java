package com.azhe;

import com.azhe.domain.query.UserQuery;
import com.azhe.mapper.UserMapper;
import com.azhe.domain.pojo.User;
import com.azhe.server.UserServer;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
@MapperScan("com.azhe.mapper")
public class TestClass {


    @Autowired
    UserMapper userMapper;
    @Autowired
    UserServer userServer;


    @Test
    public void testSelectAll() {
        System.out.println("test.............");
        userMapper.selectList(null).forEach(System.out::println);
    }

    @Test
    public void testQuery() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().
                select("id", "name", "password").
                like("name", "山大王").
                eq("age", 18);
        userMapper.selectList(queryWrapper);

        LambdaQueryWrapper<User> q2 = queryWrapper.lambda().
                select(User::getId, User::getName, User::getPassword).
                like(User::getName, "山大王");

    }

    @Test
    public void testServiceQuery() {
        userServer.lambdaQuery().eq(User::getPassword, 123456).list();
    }

    @Test
    public void testUpdate() {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(User::getName, "changed")
                .eq(User::getAge, 18);

        // ✅ 執行更新：這行才是真正作用！
        int rows = userMapper.update(null, updateWrapper);

        System.out.println("更新筆數: " + rows);
    }

    @Test
    public void testMyUpdate() {

        String name = "修改名稱";
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<User> wrapper = updateWrapper.lambda().in(User::getId, Arrays.asList(1, 2, 4));

        userMapper.myUpdate(wrapper, name);
    }

    @Test
    public void testService(){
        User user = new User();
        user.setName("changed123");
        user.setId(6);
        userServer.save( user );
    }


    @Test
    public void testInsert() {
        System.out.println("test.............");
        User user = new User();
        user.setName("山大王");
        user.setPassword("123456");
        System.out.println(user);
        userMapper.insert(user);
    }

    @Test
    public void testDeleteById() {
        System.out.println("test.............");
        userMapper.deleteById(1);
    }

    @Test
    public void testSelectById() {
        System.out.println("test.............");
        System.out.println(userMapper.selectById(2L));
    }



    private User builderUser(int i) {
        User user = new User();
        user.setName("user_" + i);
        user.setPassword("123456");
        user.setAge(i + 10);
        return user;
    }

    // 測試輸入1萬筆i=1~10000 User到持久層執行時間
    @Test
    public void testInsertBatch() {
        System.out.println("test.............");
        long b = System.currentTimeMillis();
        for (int i = 1; i <= 10000; i++) {
            userMapper.insert(builderUser(i));
        }
        long e = System.currentTimeMillis();
        System.out.println("執行時間: " + (e - b) + "ms");
    }

    // 測試輸入1萬筆i=1~10000批處理insert User到持久層，每1000個user對象放進list，共執行10次，顯示執行時間
    @Test //執行時間: 12450ms
    public void testInsertBatch2() {
        System.out.println("test.............");
        ArrayList<User> list = new ArrayList<>(1000);
        long b = System.currentTimeMillis();
        for (int i = 1; i <= 10000; i++) {
            list.add(builderUser(i));
            if (i % 1000 == 0) {
                userMapper.insertBatchSomeColumn(list);
                list.clear();
            }
        }
        long e = System.currentTimeMillis();
        System.out.println("執行時間: " + (e - b) + "ms");
    }

    // 測試使用查詢對象找資料
    @Test
    public void testQueryUser(){
        UserQuery userQuery = new UserQuery();
//        userQuery.setName("山");
        userQuery.setMaxAge(20);
        userQuery.setMinAge(19);
        userServer.queryUsers(userQuery);
    }



    @Test
    public void testUpdateBatch(){ // 執行時間: 2450ms 開啟批處理rewriteBatchedStatements=true: 864ms
        System.out.println("test.............");
        ArrayList<User> list = new ArrayList<>(1000);
        long b = System.currentTimeMillis();
        for (int i = 1; i <= 10000; i++) {
            list.add(builderUser(i));
            if (i % 1000 == 0) {
                userServer.saveBatch(list);
                list.clear();
            }
        }
        long e = System.currentTimeMillis();
        System.out.println("執行時間: " + (e - b) + "ms");
    }

    @Test
    public void testtest(){

        List<User> users = userServer.listByIds(Arrays.asList(1, 2, 3));
        Map<String, List<User>> collect = users.stream().collect(Collectors.groupingBy(User::getName));


    }

}
