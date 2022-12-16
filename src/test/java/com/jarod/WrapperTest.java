package com.jarod;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jarod.mapper.UserMapper;
import com.jarod.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class WrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        //查询name不为空的用户，并且邮箱不为空的用户，年龄大于12
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .isNotNull("name")
                .isNotNull("email")
                .ge("age",12);
        userMapper.selectList(queryWrapper).forEach(System.out::println);
    }


    @Test
    void test2(){
        //查询名字Tom
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","Tom");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    @Test
    void test3(){
        //查询年龄在19到30岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",19,30);
        Integer count = userMapper.selectCount(wrapper);
        System.out.println(count);
    }

    @Test
    void test4(){
        //查询年龄在19到30岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //左和右
        wrapper
                .notLike("name","e")
                .likeRight("email","t");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
}
    @Test
    void test5(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //id 在子查询中查出来
        wrapper.inSql("id", "select id from user where id < 3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    @Test
    void test6(){
        //通过id进行排序
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        userMapper.selectList(wrapper).forEach(System.out::println);
    }
}
