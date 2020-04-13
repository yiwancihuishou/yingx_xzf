package com.baizhi.xzf.dao;

import com.baizhi.xzf.entity.Video;
import com.baizhi.xzf.vo.VideoSearchVo;
import com.baizhi.xzf.vo.VideoVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoMapper extends Mapper<Video> {
    List<VideoVo> queryVideoVo();
    List<VideoSearchVo> queryByLikeVideoName(String content);
}