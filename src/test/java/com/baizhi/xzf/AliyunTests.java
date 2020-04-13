package com.baizhi.xzf;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AliyunTests {
    String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    String accessKeyId = "LTAI4FtdfyDzANCW9jmpa2aD";
    String accessKeySecret = "RjTe6WITC3ItN7uvQToSL9ZO09AndY";

    @Test
    public void contextLoads() {

    }

    @Test
    public void test1(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。

        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String bucketName = "yingx-xzf1";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    @Test
    public void uploadFile(){

        String bucketName="yingx-xzf";   //工作存储空间名
        String fileName="破音.mp4";     //指定上传文件名
        String localFile="C:\\Users\\xizhifeng\\Pictures\\DeskTop\\破音.mp4";    //指定本地文件路径
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, new File(localFile));

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    @Test
    public void downloadFile(){


        String bucketName = "yingx-xzf";
        String objectName = "xzf.jpg";
        String localFile="C:\\Users\\xizhifeng\\Pictures\\DeskTop\\"+objectName;

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

// 关闭OSSClient。
        ossClient.shutdown();
    }
    @Test
    public void delField(){

        String bucketName = "yingx-xzf";
        String objectName = "photo/xzf.jpg";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

// 关闭OSSClient。
        ossClient.shutdown();

    }

}