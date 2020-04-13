package com.baizhi.xzf;

import com.baizhi.xzf.dao.AdminDAO;
import com.baizhi.xzf.dao.UserMapper;
import com.baizhi.xzf.entity.Admin;
import com.baizhi.xzf.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxXzfApplicationTests {
    @Resource
    AdminDAO adminDAO;

    @Resource
    UserMapper userMapper;







    @Test
    public void contextLoads() {
        Admin admin = adminDAO.queryUsername("admin", "admin");
        System.out.println(admin);
    }

    @Test
    public void test1(){
        List<User> users = userMapper.selectAll();
        users.forEach(user -> System.out.println(user));
    }

   /* @Test
    public void test1() {
        //User user = userMapper.selectByPrimaryKey("1");
        //条件对象
       *//* UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo("1");
        List<User> users = userMapper.selectByExample(userExample);
        users.forEach(user -> System.out.println(user));*//*

       //插入
        *//*User user = new User("2","2","2","2","2","2","2",new Date());
        int insert = userMapper.insert(user);*//*

        //修改
        UserExample userExample = new UserExample();
        //userExample.createCriteria().andIdEqualTo("3");

      *//*  User user = new User();
        user.setId("3");
        user.setPhone("88888");
        userMapper.updateByExampleSelective(user,userExample);*//*

        //删除
       // userMapper.deleteByExample(userExample);
        //查询数量
        long l = userMapper.countByExample(userExample);
        System.out.println(l);
    }*/
}