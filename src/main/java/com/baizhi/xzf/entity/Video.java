package com.baizhi.xzf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Table(name = "yx_video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video implements Serializable {
    @Id
    private String id;

    private String title;

    private String brief;

    private String path;

    private String cover;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;

    private String categoryId;

    private String groupId;

    private String userId;

}