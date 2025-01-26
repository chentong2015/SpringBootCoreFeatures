package com.chentong.myblog.web;

import com.chentong.myblog.query.BlogQuery;
import com.chentong.myblog.model.Type;
import com.chentong.myblog.service.AdminService;
import com.chentong.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TypeWebController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private AdminService adminService;

    // 只用type对象的id访问到网页面，活跃的id
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, @PathVariable long id, Model model){
        List<Type> types = typeService.listTypeTop(20); // 查询top的类型，全表的类型
        String typeName; // 初始化选择的type的类型只到input选框中
        if(id == -1){ // 从导航点入的默认的id值, 取最大的那个值
            id = types.get(0).getId();
            typeName = types.get(0).getName();
        } else {
            typeName = typeService.getType(id).getName();
        }
        model.addAttribute("types", types);
        // 建立一个查询语句，根据type的id类型查询到所有的相关的博客
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);

        // th:each="blog : ${page.content}" 在UI页面一页一页的获取查询的数据
        model.addAttribute("page", adminService.listBlog(pageable, blogQuery));
        model.addAttribute("activeId", id);
        model.addAttribute("name", typeName);
        return "types";
    }

    // 使用博客的类型进行搜索
    @PostMapping("/types/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page", adminService.listBlog(pageable, blog));
        return "types :: blogList"; // 返回的页面局部的一个片段fragment，实现页面局部的刷新
    }

}
