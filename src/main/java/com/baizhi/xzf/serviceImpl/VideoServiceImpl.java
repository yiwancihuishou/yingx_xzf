package com.baizhi.xzf.serviceImpl;

import com.baizhi.xzf.annotation.AddCache;
import com.baizhi.xzf.annotation.AddLog;
import com.baizhi.xzf.annotation.DelCache;
import com.baizhi.xzf.dao.VideoMapper;
import com.baizhi.xzf.entity.Video;
import com.baizhi.xzf.entity.VideoExample;
import com.baizhi.xzf.service.VideoService;
import com.baizhi.xzf.util.AliyunOssUtil;
import com.baizhi.xzf.util.InterceptVideoPhotoUtil;
import com.baizhi.xzf.vo.VideoSearchVo;
import com.baizhi.xzf.vo.VideoVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    @Resource
    VideoMapper videoMapper;

    @Resource
    HttpSession session;

    @AddCache
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //总条数   records
        VideoExample example = new VideoExample();
        Integer records = videoMapper.selectCountByExample(example);
        map.put("records", records);

        //总页数  totals    总条数/每页展示条数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);

        //当前页  page
        map.put("page", page);
        //数据   rows   分页
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Video> videos = videoMapper.selectByRowBounds(new Video(), rowBounds);
        map.put("rows", videos);

        return map;
    }

    @DelCache
    @AddLog(value = "视频添加")
    @Override
    public String add(Video video) {
        String uid = UUID.randomUUID().toString();

        video.setId(uid); //id
        video.setPublishDate(new Date());
        video.setUserId("1");
        video.setCategoryId("1");
        video.setGroupId("1");

        System.out.println("=业务层插入数据=video==" + video);

        //向mysql添加
        videoMapper.insert(video);

        //返回添加数据的id
        return uid;
    }

    @DelCache
    @AddLog(value = "视频修改")
    @Override
    public void update(Video video) {
        videoMapper.updateByPrimaryKeySelective(video);
    }

    @DelCache
    @AddLog(value = "视频删除")
    @Override
    public HashMap<String, Object> delete(Video video) {
        HashMap<String, Object> map = new HashMap<>();
        //设置条件
        try{
            VideoExample example = new VideoExample();
            example.createCriteria().andIdEqualTo(video.getId());
            //根据id查询视频数据
            Video videos = videoMapper.selectOneByExample(example);

            //1.删除数据
            videoMapper.deleteByExample(example);
            //字符串拆分
            String[] pathSplit = videos.getPath().split("/");
            String[] coverSplit = videos.getCover().split("/");
            String videoName = "video/" + pathSplit[4];
            String coverName = "photo/" + coverSplit[4];

            /*2.删除视频
             * 删除阿里云文件
             * 参数：
             *
             *   fileName:文件名    目录名/文件名
             * */
            AliyunOssUtil.delField(videoName);
            /*3.删除封面
             * 删除阿里云文件
             * 参数：
             *   bucker: 存储空间名
             *   fileName:文件名    目录名/文件名
             * */
            AliyunOssUtil.delField(coverName);
            map.put("status", "200");
            map.put("message", "删除成功");
    }catch (Exception e) {
            e.printStackTrace();
            map.put("status", "400");
            map.put("message", "删除失败");
        }
        return map;
    }

    @Override
    public void uploadVdieo(MultipartFile path, String id, HttpServletRequest request) {
            //1文件上传到阿里云
        //获取文件名
        String filename = path.getOriginalFilename();
        String newName= new Date().getTime()+"-"+filename;

        /*
         *上传本地文件
         * 参数：
         *   headImg: 指定MultipartFile类型的文件
         *   fileName:  指定上传文件名  可以指定上传目录：  目录名/文件名
         * */
        AliyunOssUtil.uploadFileBytes(path,"video/"+newName);

        //频接视频完整路径
        String netFilePath="http://yingx-xzf.oss-cn-beijing.aliyuncs.com/video/"+newName;
        /*2.截取视频第一帧做封面
         * 获取指定视频的帧并保存为图片至指定目录
         * @param videofile 源视频文件路径
         * @param framefile 截取帧的图片存放路径
         * */
        String realPath = session.getServletContext().getRealPath("/upload/cover");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        //频接本地存放路径    D:\动画.jpg
        // 1585661687777-好看.mp4
        String[] names = newName.split("\\.");
        String interceptName=names[0];
        String coverName=interceptName+".jpg";  //频接封面名字
        String coverPath= realPath+"\\"+coverName;  //频接视频封面的本地绝对路径
        //截取封面保存到本地
        try {
            InterceptVideoPhotoUtil.fetchFrame(netFilePath,coverPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*3.将封面上传至阿里云
         *上传本地文件
         * 参数：
         *
         *   fileName:  指定上传文件名  可以指定上传目录：  目录名/文件名
         *   localFilePath: 指定本地文件路径
         * */
        AliyunOssUtil.uploadFile("photo/"+coverName,coverPath);
        //4.删除本地文件
        File file1 = new File(coverPath);
        //判断是一个文件，并且文件存在
        if(file1.isFile()&&file1.exists()){
            //删除文件
            boolean isDel = file1.delete();
            System.out.println("删除："+isDel);
        }

        //5.修改视频信息
        //添加修改条件
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);

        //修改的结果
        Video video = new Video();

        video.setPath("https://yingx-xzf.oss-cn-beijing.aliyuncs.com/video/"+newName);
        video.setCover("https://yingx-xzf.oss-cn-beijing.aliyuncs.com/photo/"+coverName);

        //调用修改方法
        videoMapper.updateByExampleSelective(video,example);

    }

    @AddCache
    @Override
    public List<VideoVo> queryVideoVo() {
        List<VideoVo> videoVos = videoMapper.queryVideoVo();
        return videoVos;
    }

    @AddCache
    @Override
    public List<VideoSearchVo> queryByLikeVideoName(String content) {
        List<VideoSearchVo> videoSearchVos = videoMapper.queryByLikeVideoName(content);

        return videoSearchVos;
    }
}
