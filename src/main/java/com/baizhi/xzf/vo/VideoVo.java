package com.baizhi.xzf.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoVo {
    private String id;
    private String videoTitle;
    private String cover;
    private String path;
    private Date uploadTime;
    private String description;
    private String cateName;
    private Integer likeCount=32;
    private String headImg;
}
