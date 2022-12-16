package com.jarod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jarod.pojo.User;
import org.springframework.stereotype.Repository;

//在对应的Mapper上面基本的类BaseMapper
@Repository//代表持久层
public interface UserMapper extends BaseMapper<User> {

    //所有的CRUD操作都已经编写完成
    //不需要像以前配置一大堆文件
}
