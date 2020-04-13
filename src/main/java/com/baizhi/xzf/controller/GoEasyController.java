package com.baizhi.xzf.controller;

import com.baizhi.xzf.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("GoEasyController")
public class GoEasyController {

    @Resource
    UserService userService;

    @RequestMapping("queryUserMap")
    @ResponseBody
    public List<Object> queryUserMap(){
        System.out.println("===================这是我的controller");
        List<Object> list = userService.queryUserMap();
        for (Object o : list) {
            System.out.println(o);
        }

        return list;
    }

}
