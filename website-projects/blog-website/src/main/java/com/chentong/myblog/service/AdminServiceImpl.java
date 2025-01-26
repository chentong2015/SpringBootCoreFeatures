package com.chentong.myblog.service;

import com.chentong.myblog.dao.AdminRepository;
import com.chentong.myblog.handler.NotFoundException;
import com.chentong.myblog.model.Blog;
import com.chentong.myblog.query.BlogQuery;
import com.chentong.myblog.model.Type;
import com.chentong.myblog.util.MarkdownUtils;
import com.chentong.myblog.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * java是单继承语言， 一个类不能直接继承多个类
 * implements 类对于接口的具体实现 Override
 * 继承接口了就必须实现接口内的所有方法 !
 */
@Service
public class AdminServiceImpl implements AdminService {

    // 接口可以实例化一个对象，执行其中的具体函数方法, 前提是给接口中的方法已经被实现过了
    // 反之 如果是抽象类，则一定不能够实例化对象
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Blog getBlog(Long id) {
        return adminRepository.getOne(id);
    }

    @Transactional
    @Override
    public Blog getBlogHtml(Long id) {
        Blog blog = adminRepository.getOne(id);
        if(blog == null) {
            throw new NotFoundException("Can not find the blog");
        }
        // 新建一个对象，避免原来数据库中的对象改变
        Blog blogHtml = new Blog();
        BeanUtils.copyProperties(blog, blogHtml);
        String content = blogHtml.getContent();
        blogHtml.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        adminRepository.updateViews(id);
        return blogHtml;
    }

    /**
     * 前端界面的直接查询方法
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    /**
     *  "多对多的关系表的查询"
     *  关联的查询：根据标签的id找到有这个tag的id的博客 List<Tag> listTags
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, Long tagid) {
        return adminRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("listTags");
                return criteriaBuilder.equal(join.get("id"), tagid); // 返回根据标签的id找到的page：所有的含改tag的id的博客
            }
        }, pageable);
    }

    /**
     * Specification 多值的匹配组合的查询 : 测试查询的数据准确性
     * 通过BlogQuery传递过来的信息，从Blog表中将数据提取出来，同时返回分页查询的结果到前端
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return adminRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // root ==> blog object 匹配的是Blog类的对象; criteriaQuery ==> 查询条件的容器;
                List<Predicate> list = new ArrayList<>(); // list 查询的条件的组合
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    list.add(criteriaBuilder.like(root.<String>get("title"), "%"+blog.getTitle()));
                }
                if(blog.getTypeId() != null ) {  //根据blog对象的type的id值进行查询
                    list.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if(blog.isRecommend()) {
                    list.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                if(blog.isPublished()) {
                    list.add(criteriaBuilder.equal(root.<Boolean>get("published"), blog.isPublished()));
                }
                criteriaQuery.where(list.toArray(new Predicate[list.size()]));
                return null;
            }
        }, pageable);
    }

    // PageRequest constructors have been deprecated
    @Override
    public List<Blog> listRecommendBlog(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return adminRepository.findTop(pageable);
    }

    /**
     * 根据年份，判断并获取到对应的博客列表
     * @return
     */
    @Override
    public Map<String, List<Blog>> listArchivesBlog() {
        List<String> years = adminRepository.findGroupByYear();
        Map<String, List<Blog>> mapListBlog = new HashMap<>();
        for(String year: years){
            mapListBlog.put(year, adminRepository.findByYear(year));
        }
        return mapListBlog;
    }

    @Override
    public Long countBlogs() {
        return adminRepository.count();
    }

    // 实现搜索博客的标题或者内容
    @Override
    public Page<Blog> listSearchBlog(String query, Pageable pageable) {
        return adminRepository.findBySearch(query, pageable);
    }

    /**
     * 判断保存blog博客的时候 是否是保存的博客的草稿，有没有id存在
     */
    @Transactional // 放到事务中来
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0); // 设置初始的浏览的次数
            blog.setGood(0); // 初始化点赞的次数
        } else {
            blog.setUpdateTime(new Date());
        }
        return adminRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1 = adminRepository.getOne(id);
        if(blog1 == null) {
            /**
             * Throw 抛出异常：当方法中可能存在异常时，不在方法函数中处理，而是将异常抛出
             * Throw new Exception("对一异常的描述");
             * 可以自定义异常类Exception，使用继承的方式
             */
            throw new NotFoundException("Can not find the blog");
        }
        // blog => blog1, 过滤掉blog中属性值为空的属性, 保存到
        BeanUtils.copyProperties(blog, blog1, MyBeanUtils.getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());
        return adminRepository.save(blog1);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {

        adminRepository.deleteById(id);
    }

}

