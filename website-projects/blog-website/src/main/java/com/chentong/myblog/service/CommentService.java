package com.chentong.myblog.service;

import com.chentong.myblog.model.Comment;

import java.util.List;

/**
 * 处理的评论的接口类，评论的提交，获取与显示
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long id);

    Comment saveComment(Comment comment);

    void updateGoodBlog(Long id);

}
