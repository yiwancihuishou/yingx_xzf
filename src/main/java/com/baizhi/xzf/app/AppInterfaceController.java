package com.baizhi.xzf.app;

import com.baizhi.xzf.common.CommonResult;
import com.baizhi.xzf.service.CategoryService;
import com.baizhi.xzf.service.VideoService;
import com.baizhi.xzf.util.AliyunSendPhoneUtil;
import com.baizhi.xzf.vo.CategoryVo;
import com.baizhi.xzf.vo.VideoSearchVo;
import com.baizhi.xzf.vo.VideoVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("app")
public class AppInterfaceController {
    @Resource
    VideoService videoService;
    @Resource
    CategoryService categoryService;

    @RequestMapping("getPhoneCode")
    public CommonResult getPhoneCode(String phone){

        CommonResult commonResult = new CommonResult();

        String random = AliyunSendPhoneUtil.getRandom(6);
        System.out.println("存储验证码："+random);
        //发送验证码
        String message = AliyunSendPhoneUtil.sendCode(phone, random);
        if (message.equals("发送成功")){
            commonResult.setStatus("100");
            commonResult.setMessage(message);
            commonResult.setData(phone);
        }else{
            commonResult.setStatus("104");
            commonResult.setMessage("发送失败："+message);
            commonResult.setData(null);
        }
        return commonResult;
    }
    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime(){
        CommonResult commonResult = new CommonResult();

        try {
            List<VideoVo> videoVos = videoService.queryVideoVo();
            commonResult.setData(videoVos);
            commonResult.setMessage("查询成功");
            commonResult.setStatus("100");
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setMessage("查询失败");
            commonResult.setStatus("104");
        }

        return commonResult;
    }

    @RequestMapping("queryAllCategory")
    public CommonResult queryAllCategory(){
        CommonResult commonResult = new CommonResult();

        try {
            List<CategoryVo> categoryVos = categoryService.queryAllCategory();
            commonResult.setData(categoryVos);
            commonResult.setMessage("查询成功");
            commonResult.setStatus("100");
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setMessage("查询失败");
            commonResult.setStatus("104");
        }

        return commonResult;
    }

    @RequestMapping("queryByLikeVideoName")
    public CommonResult queryByLikeVideoName(String content){
        CommonResult commonResult = new CommonResult();

        try {
            List<VideoSearchVo> videoSearchVos = videoService.queryByLikeVideoName(content);
            commonResult.setData(videoSearchVos);
            commonResult.setMessage("查询成功");
            commonResult.setStatus("100");
        } catch (Exception e) {
            e.printStackTrace();
            commonResult.setMessage("查询失败");
            commonResult.setStatus("104");
        }

        return commonResult;
    }
}

