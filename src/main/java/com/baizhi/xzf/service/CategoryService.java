package com.baizhi.xzf.service;

import com.baizhi.xzf.entity.Category;
import com.baizhi.xzf.vo.CategoryVo;

import java.util.HashMap;
import java.util.List;

public interface CategoryService {
    HashMap<String,Object> queryByPage(Integer page, Integer rows,String levels,String id);
    void add(Category category,String parentId);
    void update(Category category);
    HashMap<String,Object> del(Category category);

    //前台查询类别
    List<CategoryVo> queryAllCategory();
}
