package com.baizhi.xzf;

import com.alibaba.fastjson.JSON;
import io.goeasy.GoEasy;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class GoEasyTest {
   @Test
    public void testQuerys(){
       //配置发送消息的必要参数  参数：regionHost；服务器地址 appkey  自己的appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-39e920b889fe4ba7b7cea59710167de2");
        //参数;channel自己的管道名称 自定义   content 发送的内容
        goEasy.publish("xzf_channel", "Hello, xzf_GoEasy!");
   }

    @Test
    public void testQuerysUser(){
        for (int i = 0; i < 11; i++) {
            Random random = new Random();
            //获取随机数 参数  参数50 取0~50的随机数
            // int i = random.nextInt(50);

            HashMap<String, Object> map = new HashMap<>();
            //根据月份 性别 查询数量
            map.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月"));
            map.put("boys", Arrays.asList(random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(50),random.nextInt(500),random.nextInt(500)));
            map.put("girls", Arrays.asList(random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(50),random.nextInt(500),random.nextInt(500)));

            String content = JSON.toJSONString(map);

            //配置发送消息的必要参数  参数：regionHost；服务器地址 appkey  自己的appkey
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-39e920b889fe4ba7b7cea59710167de2");
            //参数;channel自己的管道名称 自定义   content 发送的内容
            goEasy.publish("xzf_channel", content);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}