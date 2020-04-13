package com.baizhi.xzf.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Table(name = "yx_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User  implements Serializable {
    @Excel(name = "ID",width = 20)
    private String id;
    @Excel(name = "用户名")
    private String username;
    @Excel(name = "电话号",width = 20)
    private String phone;
    @Excel(name = "头像")
    private String headImg;
    @Excel(name = "简介")
    private String sign;
    @Excel(name = "微信")
    private String wechat;
    @Excel(name = "状态")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_date")
    @Excel(name = "创建时间",format = "yyyy年MM月dd日",width = 20)
    private Date createDate;
}