package com.baizhi.xzf.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ExcelTarget(value = "student")
public class Student implements Serializable {

    @Excel(name="ID")
    private String id;
    @Excel(name="名字")
    private String name;
    @Excel(name="年龄")
    private Integer age;
    @Excel(name="生日",format = "yyyy年MM月dd日",width = 20)
    private Date bir;
}
