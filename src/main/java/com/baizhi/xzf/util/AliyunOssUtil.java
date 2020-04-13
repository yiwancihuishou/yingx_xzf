package com.baizhi.xzf.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class AliyunOssUtil {

    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    private static String accessKeyId = "LTAI4FtdfyDzANCW9jmpa2aD";
    private static String accessKeySecret = "RjTe6WITC3ItN7uvQToSL9ZO09AndY";

    private static String bucket="yingx-xzf";

    /*
    *上传本地文件
    * 参数：
    *   bucket:  存储空间名
    *   fileName:  指定上传文件名  可以指定上传目录：  目录名/文件名
    *   localFilePath: 指定本地文件路径
    * */
    public static void uploadFile(String fileName,String localFilePath){

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, new File(localFilePath));

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /*
     *上传本地文件
     * 参数：
     *   headImg: 指定MultipartFile类型的文件
     *   fileName:  指定上传文件名  可以指定上传目录：  目录名/文件名
     * */
    public static void uploadFileBytes(MultipartFile headImg, String fileName){

        //转为字节数组
        byte[] bytes = new byte[0];
        try {
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);

        // 上传Byte数组。
        ossClient.putObject(bucket, fileName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();
    }


    /*
    * 下载的文件名  objectName
    * 要下载到的下载的路径
    *
    * */
    public static void downloadFile(String objectName,String localFile){




        String localFile2=localFile+objectName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucket, objectName), new File(localFile2));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /*
    * objectName  要删除的文件名
    *
    *
    * */
    public static void delField(String objectName){


// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucket, objectName);

// 关闭OSSClient。
        ossClient.shutdown();
    }

    public static void main(String[] args) {
        String bucket="yingx-186";   //存储空间名
        String fileName="2020视频.mp4";  //指定上传文件名  可以指定上传目录
        String localFilePath="C:\\Users\\NANAN\\Desktop\\other\\video\\2020宣传视频.mp4";  //指定本地文件路径

        //调用测试方法
        uploadFile(fileName,localFilePath);
    }
}
