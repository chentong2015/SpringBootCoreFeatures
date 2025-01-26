package com.chentong.myblog.service;

import com.chentong.myblog.model.Blog;
import com.chentong.myblog.query.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 接口只是一个协议; 通过类的继承来实现方法
 */
public interface AdminService {

    // 接口中的方法，没有定义实现的过程
    Blog getBlog(Long id);

    // 获取博客之后使用转换工具进行转换成html
    Blog getBlogHtml(Long id);

    // 使用于前端直接的获取到的所有page的数据
    Page<Blog> listBlog(Pageable pageable);

    // 根据tag标签的id查询所有的相关联的博客
    Page<Blog> listBlog(Pageable pageable, Long tagid);

    // 使用于后端获取到的带有query查询的page的结果
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    // 拿到最新推荐的博客的列表数据
    List<Blog> listRecommendBlog(Integer size);

    Map<String, List<Blog>> listArchivesBlog();

    Long countBlogs();

    Page<Blog> listSearchBlog(String query, Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

}
