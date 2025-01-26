package com.chentong.myblog.service;

import com.chentong.myblog.dao.CommentRepository;
import com.chentong.myblog.model.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    /**
     *  改List容器存放一个顶级评论的所有的的子级回复的评论信息列表
     */
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 出来取出来的评论的list表，处理成层级的关系
     */
    @Override
    public List<Comment> listCommentByBlogId(Long id) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        List<Comment> comments = commentRepository.findCommentsByBlogIdAndParentCommentIsNull(id, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        /**
         * 如果有父类的评论，则设置当前的评论的父类的评论，反之就是顶级的父级，设置父类的评论为空
         */
        if(parentCommentId != -1){
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 更新对应博客的点赞的数目
     * @param id
     */
    @Override
    public void updateGoodBlog(Long id) {
        commentRepository.updateGoodBlog(id);
    }

    /**
     * 循环每个顶级的节点，copy出来，不更改原来数据库中的内容
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for(Comment comment: comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * 针对每一个顶级的评论，合并评论的各个层次子层到第一级的子集中
     */
    private void combineChildren(List<Comment> comments){
        for(Comment comment: comments) {
            // 拿到直接的回复的层级的评论
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1: replys1){
                recursively(reply1); // 循环迭代找出子代，放到tempReplys中
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //处理完一个顶级的评论后，清除临时的存放区，用于放置下月个评论的所以子级节点评论
            tempReplys = new ArrayList<>();
        }
    }

    /**
     * 递归迭代函数, 将所有的层级下的回复都统一到一个层级
     * 迭代找出来之后添加
     */
   private void recursively(Comment comment){
       tempReplys.add(comment);
       if(comment.getReplyComments().size() > 0){
           List<Comment> replys = comment.getReplyComments();
           for(Comment reply: replys){
               tempReplys.add(reply);
               if(reply.getReplyComments().size() >0){
                   recursively(reply);
               }
           }
       }
   }

}
