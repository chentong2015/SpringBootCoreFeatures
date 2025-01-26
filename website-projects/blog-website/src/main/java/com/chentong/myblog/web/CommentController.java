package com.chentong.myblog.web;

import com.chentong.myblog.model.Comment;
import com.chentong.myblog.model.User;
import com.chentong.myblog.service.AdminService;
import com.chentong.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AdminService adminService;

    // 定义在配置文件中的全局的属性变量值
    @Value("${comment.avatar}")
    private String avatar;

    /** 返回页面的一个片段
     *  加载界面的时候，需要首先将评论加载出来显示
     */
    @GetMapping("/comments/{blogId}")
    public String commentsList(@PathVariable Long blogId, Model model){
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        // 必须设置前端的th:fragment片段的属性
        return "blog :: commentList";
    }

    /**
     * 提价评论的方法, 接受传来的Comment数据
     * 保存的时候 ：判断是否有父类的评论，添加时间，设置该评论对应的博客, 是否是管理员评论的信息
     * @param session 用于前端判断的用户的session数据
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(adminService.getBlog(blogId));
        if(comment.getEmail() == null || comment.getEmail().equals("")){
            comment.setEmail("user@email.com");
        }
        User user = (User) session.getAttribute("user");
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdmin(true);
            comment.setNickname(user.getNickname());
        } else {
            comment.setAvatar(avatar);
            comment.setAdmin(false);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+ blogId;  // 点击之后调用的函数获取评论，刷新页面的片段区域
    }

    /**
     * 点赞之后增加该博客的点赞的数目; 采用页面的片段加载，不重新刷新界面的效果
     */
    @GetMapping("/good/{blogId}")
    public String good(@PathVariable Long blogId){
        commentService.updateGoodBlog(blogId);
        return "redirect:/comments/"+ blogId;
    }

}
