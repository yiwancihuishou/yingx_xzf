package com.baizhi.xzf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@tk.mybatis.spring.annotation.MapperScan("com.baizhi.xzf.dao")

@SpringBootApplication
public class YingxXzfApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxXzfApplication.class, args);
    }

}
