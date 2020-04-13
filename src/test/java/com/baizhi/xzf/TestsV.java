package com.baizhi.xzf;

import com.baizhi.xzf.dao.CategoryMapper;
import com.baizhi.xzf.dao.UserMapper;
import com.baizhi.xzf.dao.VideoMapper;
import com.baizhi.xzf.entity.City;
import com.baizhi.xzf.service.CategoryService;
import com.baizhi.xzf.service.VideoService;
import com.baizhi.xzf.vo.CategoryVo;
import com.baizhi.xzf.vo.VideoSearchVo;
import com.baizhi.xzf.vo.VideoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestsV {
    @Resource
    VideoMapper videoMapper;
    @Resource
    CategoryMapper categoryMapper;
    @Resource
    CategoryService categoryService;
    @Resource
    VideoService videoServicel;

    @Resource
    UserMapper userMapper;
    @Test
    public void test1(){
        List<VideoVo> videoVos = videoMapper.queryVideoVo();
        for (VideoVo videoVo : videoVos) {
            System.out.println(videoVo);
        }
    }

    @Test
    public void test2(){
        List<CategoryVo> categoryVos = categoryMapper.queryAllOne();
        for (CategoryVo categoryVo : categoryVos) {
            System.out.println(categoryVo);
        }
    }
    @Test
    public void test3(){
        List<CategoryVo> categoryVos = categoryMapper.queryAllTwo("3");
        for (CategoryVo categoryVo : categoryVos) {
            System.out.println(categoryVo);
        }

    }
    @Test
    public void test4(){
        List<CategoryVo> categoryVos = categoryService.queryAllCategory();
        for (CategoryVo categoryVo : categoryVos) {
            System.out.println(categoryVo);
        }

    }

    @Test
    public void test5(){
        List<VideoSearchVo> videoSearchVos = videoServicel.queryByLikeVideoName("465489856");
        System.out.println(videoSearchVos.isEmpty());

    }


    @Test
    public void test6(){
        List<City> cities = userMapper.queryCity("女");
        for (City city : cities) {
            System.out.println(city);
        }

    }

}