package com.baizhi.xzf.dao;

import com.baizhi.xzf.entity.City;
import com.baizhi.xzf.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper  extends Mapper<User> {
    //用户地图查询
    List<City> queryCity(String sex);

}