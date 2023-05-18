package com.plate.msgService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.plate.msgService.entity.Comment;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author czh
 * @since 2022-05-02
 */
public interface CommentService extends IService<Comment> {

    Map<String, Object> getCommentFrontList(Page<Comment> pageComment);

    Map<String, Object> selectCommentByTrendId(Page<Comment> pageComment, String trendId);
}
