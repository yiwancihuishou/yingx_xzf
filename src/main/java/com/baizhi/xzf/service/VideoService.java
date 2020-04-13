package com.baizhi.xzf.service;

import com.baizhi.xzf.entity.Video;
import com.baizhi.xzf.vo.VideoSearchVo;
import com.baizhi.xzf.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface VideoService {
    HashMap<String, Object> queryByPage(Integer page, Integer rows);

    String add(Video video);

    void update(Video video);

    HashMap<String, Object> delete(Video video);

    void uploadVdieo(MultipartFile path, String id, HttpServletRequest request);
    List<VideoVo> queryVideoVo();

    List<VideoSearchVo> queryByLikeVideoName(String content);

}
