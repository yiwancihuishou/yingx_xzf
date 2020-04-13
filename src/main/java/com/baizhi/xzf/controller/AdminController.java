package com.baizhi.xzf.controller;

import com.baizhi.xzf.entity.Admin;
import com.baizhi.xzf.service.AdminService;
import com.baizhi.xzf.util.ImageCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Resource
    AdminService adminService;


    @RequestMapping("getImageCode")
    public void getImageCode(HttpServletRequest request, HttpServletResponse response){
        //1根据验证码工具类获取随机字符
        String code = ImageCodeUtil.getSecurityCode();
        System.out.println(code);
        //2存储随机字符
        request.getSession().setAttribute("imageCode",code);
        //3将验证码生成图片
        BufferedImage image = ImageCodeUtil.createImage(code);
        //4响应到前台页面
        try {
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("login")
    @ResponseBody
    public HashMap<String,Object> login(Admin admin,String enCode){
        //调用业务方法
        HashMap<String, Object> map = adminService.login(admin, enCode);
        return map;
    }
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
       request.getSession().removeAttribute("admin");
       return "redirect:/login/login.jsp";
    }
}
