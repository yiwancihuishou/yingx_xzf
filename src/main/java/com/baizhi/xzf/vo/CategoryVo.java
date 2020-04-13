package com.baizhi.xzf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {
    private String id;
    private String cateName;
    private String levels;
    private String parentId;
    private List<CategoryVo> categoryList;
}
