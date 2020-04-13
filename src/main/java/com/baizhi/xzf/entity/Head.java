package com.baizhi.xzf.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Head implements Serializable {
    @Excel(name="ID")
    private String id;
    @Excel(name="名字")
    private String name;
    @Excel(name="年龄")
    private Integer age;
    @Excel(name="生日",format = "yyyy年MM月dd日",width = 20)
    private Date bir;
    @Excel(name="头像", type=2)
    private String headImg;
}
