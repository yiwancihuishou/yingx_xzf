package com.baizhi.xzf.serviceImpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baizhi.xzf.annotation.AddCache;
import com.baizhi.xzf.annotation.AddLog;
import com.baizhi.xzf.annotation.DelCache;
import com.baizhi.xzf.dao.UserMapper;
import com.baizhi.xzf.entity.City;
import com.baizhi.xzf.entity.Model;
import com.baizhi.xzf.entity.User;
import com.baizhi.xzf.entity.UserExample;
import com.baizhi.xzf.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    //分页查询
    @AddCache
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();

        //总条数
        Integer records = userMapper.selectCount(new User());
        map.put("records",records);

        //总页数 total  三元运算符  总条数%上每页展示几条是否有余数 有余数的话+1
        Integer total=records % rows==0? records/rows:records/rows+1;
        map.put("total",total);
        //当前页
        map.put("pager",page);
        //数据
        //RowBounds参数 忽略条数，获取几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<User> users = userMapper.selectByRowBounds(new User(), rowBounds);
        map.put("rows",users);
        return map;
    }
    @DelCache
    @AddLog(value = "用户添加")
    @Override
    public String add(User user) {
        String UUId = UUID.randomUUID().toString();
        user.setId(UUId);
        user.setStatus("1");
        user.setCreateDate(new Date());
        userMapper.insert(user);
        return UUId;
    }

    @Override
    public void uploadUser(MultipartFile headImg, String id, HttpServletRequest request) {
        //根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");

        File file = new File(realPath);
        //判断该路径下文件夹是否存在  文件夹不存在 创建一个
        if (!file.exists()){
            file.mkdirs();
        }
        //获取文件名
        String filename = headImg.getOriginalFilename();
        String newName=new Date().getTime()+"-"+filename;//给文件拼上一个事件戳
        //文件上传
        try {
            //文件上传
            headImg.transferTo(new File(realPath,newName));
            //图片名字修改
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(id);
            User user = new User();
            user.setHeadImg(newName);//将图片的新名字存入数据库
            //修改图片名字
            userMapper.updateByExampleSelective(user,userExample);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DelCache
    @AddLog(value = "用户修改")
    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @DelCache
    @AddLog(value = "用户删除")
    @Override
    public void deleUser(User user) {
        userMapper.delete(user);
    }



    @Override
    public void uploadUserAliyun(MultipartFile headImg, String id, HttpServletRequest request) {

        //将文件转为byte数组
        byte[] bytes=null;
        try {
           bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将文件上传到阿里云

        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI4FtdfyDzANCW9jmpa2aD";
        String accessKeySecret = "RjTe6WITC3ItN7uvQToSL9ZO09AndY";
        String bucketName="yingx-xzf";   //工作存储空间名


        //获取文件名
        String filename = headImg.getOriginalFilename();
        String newName=new Date().getTime()+"-"+filename;//给文件拼上一个事件戳

        String fileName="photo/"+newName;     //指定上传文件名

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传Byte数组。

        ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();

        //修改图片信息
        //图片名字修改
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(id);
        User user = new User();
        user.setHeadImg("http://yingx-xzf.oss-cn-beijing.aliyuncs.com/photo/"+newName);//将图片的新名字存入数据库

        //修改图片名字
        userMapper.updateByExampleSelective(user,userExample);

    }
    //导出所有用户数据
    @Override
    public void outPutUserExcel() {
        List<User> users = userMapper.selectAll();

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息一览表", "工作簿1"), User.class, users);
        try {
            workbook.write(new FileOutputStream(new File("C:\\Users\\xizhifeng\\Desktop\\users.xls")));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AddCache
    //地图分布
    @Override
    public List<Object> queryUserMap() {
        ArrayList<Object> list = new ArrayList<>();

        List<City> cityBoy = userMapper.queryCity("男");
        Model boyM = new Model("小男孩", cityBoy);

        List<City> cityGirl = userMapper.queryCity("女");
        Model girlM = new Model("小女孩", cityGirl);
        list.add(boyM);
        list.add(girlM);
        return list;
    }
}
