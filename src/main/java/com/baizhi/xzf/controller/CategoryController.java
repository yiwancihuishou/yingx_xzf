package com.baizhi.xzf.controller;


import com.baizhi.xzf.entity.Category;
import com.baizhi.xzf.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Resource
    CategoryService categoryService;

    @RequestMapping("queryByPage")
    @ResponseBody
    public HashMap<String,Object> queryByPage(Integer page, Integer rows,String levels,String id){

        HashMap<String, Object> map = categoryService.queryByPage(page, rows,levels,id);

        return map;
    }
    //写操作
    @RequestMapping("edit")
    @ResponseBody
    public Object edit(Category category,String oper,String parentId){

        if("add".equals(oper)){
            categoryService.add(category,parentId);

        } if("edit".equals(oper)){
            categoryService.update(category);
        } if("del".equals(oper)){
            HashMap<String, Object> map = categoryService.del(category);
            return map;
        }
        return null;
    }

}
