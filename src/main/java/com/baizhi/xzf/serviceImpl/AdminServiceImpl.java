package com.baizhi.xzf.serviceImpl;

import com.baizhi.xzf.dao.AdminDAO;
import com.baizhi.xzf.entity.Admin;
import com.baizhi.xzf.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    HttpSession session;
    @Resource
    AdminDAO adminDAO;


    @Override
    public HashMap<String, Object> login(Admin admin, String code) {

        HashMap<String, Object> map = new HashMap<>();

        //获取存储的验证码
        String imageCode = (String) session.getAttribute("imageCode");
        //验证验证码
        if(imageCode.equals(code)){
            //验证码正确
            //验证用户 查询用户
            Admin value = adminDAO.queryUsername(admin.getUsername(), admin.getPassword());
            if (value!=null){
                //查询到用户
                session.setAttribute("admin",value);
                map.put("status","200");
                map.put("message","登陆成功！");
            }else{
                //没有查询到用户
                map.put("status","400");
                map.put("message","用户名或密码错误！");
            }

        }else{
            //验证码不正确
            map.put("status","400");
            map.put("message","验证码错误！");
        }
        //验证用户
        //验证密码
        return map;
    }
}

