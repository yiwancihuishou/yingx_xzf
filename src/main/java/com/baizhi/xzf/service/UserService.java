package com.baizhi.xzf.service;

import com.baizhi.xzf.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


public interface UserService {
   HashMap<String,Object> queryByPage(Integer page, Integer rows);
    String add(User user);
    void uploadUser(MultipartFile headImg, String id, HttpServletRequest request);
    void update(User user);
    void deleUser(User user);
    void uploadUserAliyun(MultipartFile headImg, String id, HttpServletRequest request);
    void outPutUserExcel();
    //查询用户地图分布
    List<Object> queryUserMap();

}
