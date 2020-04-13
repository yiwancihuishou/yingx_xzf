package com.baizhi.xzf.dao;

import com.baizhi.xzf.entity.Category;
import com.baizhi.xzf.vo.CategoryVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper  extends Mapper<Category> {
    List<CategoryVo> queryAllOne();
    List<CategoryVo> queryAllTwo(String parentId);
}