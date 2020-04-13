package com.baizhi.xzf.serviceImpl;

import com.baizhi.xzf.annotation.AddCache;
import com.baizhi.xzf.annotation.AddLog;
import com.baizhi.xzf.annotation.DelCache;
import com.baizhi.xzf.dao.CategoryMapper;
import com.baizhi.xzf.entity.Category;
import com.baizhi.xzf.entity.CategoryExample;
import com.baizhi.xzf.service.CategoryService;
import com.baizhi.xzf.vo.CategoryVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;
    //查询
    @AddCache
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows,String levels,String id) {
        HashMap<String, Object> map = new HashMap<>();
        Category category = new Category();
        if(id==null){
            category.setLevels(levels);
        }else{
            category.setParentId(id);
        }
        //总条数
        Integer records = categoryMapper.selectCount(category);
        //System.out.println("总条数为"+records);
        map.put("records",records);

        //总页数 total  三元运算符  总条数%上每页展示几条是否有余数 有余数的话+1
        Integer total=records % rows==0? records/rows:records/rows+1;
        map.put("total",total);
        //当前页
        map.put("pager",page);
        //数据
        //RowBounds参数 忽略条数，获取几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Category> Categorys = categoryMapper.selectByRowBounds(category, rowBounds);
        map.put("rows",Categorys);
        return map;
    }

    @DelCache
    @AddLog(value = "添加类别")
    @Override
    public void add(Category category,String parentId) {
        category.setId(UUID.randomUUID().toString());
        if (parentId!=null){
            category.setLevels("2");
            category.setParentId(parentId);
        }else{
            category.setLevels("1");
        }


        categoryMapper.insert(category);
    }

    @DelCache
    @AddLog(value = "修改类别")
    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @DelCache
    @AddLog(value = "删除类别")
    @Override
    public HashMap<String,Object> del(Category category) {
        HashMap<String, Object> map = new HashMap<>();

        Category one = categoryMapper.selectOne(category);
        if(one.getLevels().equals("1")){
            //判断该一级类别下有没有视频
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentIdEqualTo(category.getId());

            int count = categoryMapper.selectCountByExample(example);
            if(count==0){
                //该一级类别下没有二级类别
                categoryMapper.delete(category);
                map.put("status","200");
                map.put("message","删除成功！");
            }else{
                //该一级类别下有二级类别
                map.put("status","400");
                map.put("message","删除失败，该一级类别下有二级类别！");

            }

        }else{
            //判断二级类别下有没有视频
            categoryMapper.delete(category);
            map.put("status","200");
            map.put("message","删除成功！");
        }

    return map;
    }
    //前台APP查询所有的类别
    @AddCache
    @Override
    public List<CategoryVo> queryAllCategory() {
        List<CategoryVo> cateOne = categoryMapper.queryAllOne();
        for (CategoryVo categoryVo : cateOne) {
            List<CategoryVo> cateTwo = categoryMapper.queryAllTwo(categoryVo.getId());
            categoryVo.setCategoryList(cateTwo);
        }

        return cateOne;
    }
}
