package com.jarod;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jarod.mapper.UserMapper;
import com.jarod.pojo.User;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        //参数是一个Wrapper，条件构造器，这里我们先不用 null
        //查询全部用户
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
//测试插入
@Test
    public void testInset(){
        User user=new User();
        user.setId(4L);
        user.setName("java23");
        user.setAge(3);
        user.setEmail("1212334@qq.com");

        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user);
    }
    //测试更新
    @Test
    public void testUpdate(){
        User user=new User();
        user.setId(6L);
        user.setAge(21);
        user.setName("狂神说Java 改后");

        int i=userMapper.updateById(user);
        System.out.println(i);
    }

    //测试乐观锁成功
    @Test
    public void testOptimisticLocker(){
        //1.查询用户信息
        User user=userMapper.selectById(1L);
        //2.修改用户信息
        user.setName("jarod");
        user.setEmail("12313221@qq.com");
        //执行更新操作
        userMapper.updateById(user);
    }
    //测试乐观锁失败 多线程
    @Test
    public void testOptimisticLocker2(){
        //线程1
        //1.查询用户信息
        User user=userMapper.selectById(1L);
        //2.修改用户信息
        user.setName("jarod");
        user.setEmail("12313221@qq.com");

        //模拟另外一个线程执行了插队操作
        User user2=userMapper.selectById(1L);
        user2.setName("jarod222");
        user2.setEmail("22222@qq.com");
        userMapper.updateById(user2);

        //自旋锁来多次尝试提交
        userMapper.updateById(user);//如果没有乐观锁就会覆盖插队线程的值
    }


    //测试查询
    @Test
    public void testSelectById(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    //条件查询map
    @Test
    public void testSelectByBatchIds(){
        HashMap<String, Object>map=new HashMap<>();
        map.put("name","jack");
        map.put("age",20);
        List<User> users=userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //测试分页查询
    @Test
    public void testPage(){
        //参数一：当前页
        //参数二：页面大小
        Page<User> page = new Page<>(1,5);
        userMapper.selectPage(page,null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }

    //测试删除
    @Test
    public void testDeleteById(){
        userMapper.deleteById(2L);
    }

    //批量删除
    @Test
    public void testDeleteBatchId(){
        userMapper.deleteBatchIds(Arrays.asList(1414934103357992961L,5L));
    }

    //通过map删除
    @Test
    public void testDeleteMap(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("name","Sandy");
        userMapper.deleteByMap(map);
    }

}
