package com.baizhi.xzf.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Table(name = "yx_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log implements Serializable {

    @Excel(name = "ID",width = 20)
    private String id;
    @Excel(name = "管理员名字",width = 20)
    private String adminName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间",width = 20,format = "yyyy年MM月dd日 HH时mm分ss秒")
    private Date date;
    @Excel(name = "调用方法",width = 20)
    private String operation;
    @Excel(name = "操作结果",width = 20)
    private String status;


}