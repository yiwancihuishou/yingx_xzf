package com.baizhi.xzf.controller;

import com.baizhi.xzf.entity.User;
import com.baizhi.xzf.service.UserService;
import com.baizhi.xzf.util.AliyunSendPhoneUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("user")
public class UserController {
    @Resource
    UserService userService;

    @RequestMapping("queryByPage")
    @ResponseBody
    public HashMap<String,Object> queryByPage(Integer page,Integer rows){
        HashMap<String, Object> map = userService.queryByPage(page, rows);
        return map;
    }



    @RequestMapping("edit")
    @ResponseBody
    public String edit(User user, String oper){
        String uid=null;
        if(oper.equals("add")){
            uid = userService.add(user);
        }
        if (oper.equals("edit")){
            userService.update(user);
        }
        if (oper.equals("del")){
            userService.deleUser(user);
        }


        return uid;
    }
    //文件上传
    @RequestMapping("uploadUser")
    public void uploadUser(MultipartFile headImg, String id, HttpServletRequest request){
        //上传到本地
        //userService.uploadUser(headImg, id, request);
        //上传到阿里云
        userService.uploadUserAliyun(headImg,id,request);

    }

    //手机验证码
    @RequestMapping("sendPhoneCode")
    @ResponseBody
    public String sendPhoneCode(String phone){
        String code = AliyunSendPhoneUtil.getRandom(6);
        System.out.println(code);
        String message = AliyunSendPhoneUtil.sendCode(phone, code);
        System.out.println(message);
        return message;

    }

    //用户数据导出
    @RequestMapping("outPutUserExcel")
    @ResponseBody
    public void outPutUserExcel(){

        userService.outPutUserExcel();

    }
}
