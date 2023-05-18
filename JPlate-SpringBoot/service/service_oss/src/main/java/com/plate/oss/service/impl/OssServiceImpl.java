package com.plate.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.plate.oss.service.OssService;
import com.plate.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * create by chenzhihui on 2022/4/19.
 **/

@Service
public class OssServiceImpl implements OssService {

    private ConstantPropertiesUtils ConstantPropertiesUtil;
    private Object ResultCodeEnum;

    //上传文件到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {


        //通过工具类获取阿里云存储相关常量
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;


        try {
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);


            //获取上传文件流
            InputStream inputStream = file.getInputStream();

            //获取文件的名称
            String fileName = file.getOriginalFilename();
            
//            //方法1、在文件名称里面添加上随机唯一的值
//            String uuid = UUID.randomUUID().toString().replaceAll("-","");
//
//            fileName = uuid + fileName;

            //方法2、把文件按照日期进行分类
            //2022/4/19/01.png
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd/");

            fileName = datePath +fileName;

            //调用oss方法实现上传
            //第一个参数：bucket名称
            //第二个参数：上传到oss的文件路径和文件名称
            //第三个参数：上传文件输入流
            ossClient.putObject(bucketName,fileName,inputStream);

            //上传文件之后的文件路径返回 https://jmu-plate.oss-cn-beijing.aliyuncs.com/2022/04/19/C%3A%5CUsers%5CWangZai%5CPictures%5CCamera%20Roll%5CWIN_20220326_14_42_11_Pro.jpg
            String url = "https://" + bucketName + "." + endPoint + "/" + fileName;

            return url;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
