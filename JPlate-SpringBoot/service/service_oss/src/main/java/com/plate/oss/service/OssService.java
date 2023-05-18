package com.plate.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * create by chenzhihui on 2022/4/19.
 **/
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
