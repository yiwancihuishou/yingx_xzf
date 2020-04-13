package com.baizhi.xzf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emp implements Serializable {

    private String id;
    private String name;
    private Integer age;
    private Date bir;
}
