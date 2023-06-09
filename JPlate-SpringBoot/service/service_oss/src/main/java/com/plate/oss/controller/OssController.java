package com.plate.oss.controller;

import com.plate.commonutils.R;
import com.plate.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * create by chenzhihui on 2022/4/19.
 **/

@RestController
@RequestMapping("/oss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping("addFile")
    public R uploadOssFile(MultipartFile file){

        //获取上传文件
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        System.out.println(file.getOriginalFilename());
        return R.ok().data("url",url);

    }
}
