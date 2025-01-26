package com.chentong.myblog.web;

import com.chentong.myblog.service.AdminService;
import com.chentong.myblog.service.TagService;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Web Controller 页面控制器  ==> 在控制器中定义路径要执行的函数, 功能
 *  >> 找到显示的页面
 *  出错显示500错误页面
 *  没有找到路径显示404页面
 *  error界面是自定义所拦截的错误信息
 *
 *  *** 在Controller端只能够是调用到service的函数，不可以使用dao层次的函数直接操作数据库 ***
 */
@Controller
public class IndexController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page", adminService.listBlog(pageable)); // 获取数据到model中
        model.addAttribute("types", typeService.listTypeTop(6)); // 显示的Type的数目
        model.addAttribute("tags", tagService.listTagTop(8)); // 显示的Tag的数目
        model.addAttribute("recommendBlogs", adminService.listRecommendBlog(8));
        return "index";
    }

    /**
     * 访问博客的详情界面，每刷新一次，浏览次数提高
     * 在getBlogHtml()函数中操作浏览的次数
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog", adminService.getBlogHtml(id));
        return "blog";
    }

    // 使用 @RequestParam 拿到form表单传入的input数据
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model, @RequestParam String query){
        model.addAttribute("page", adminService.listSearchBlog("%"+query+"%", pageable));
        model.addAttribute("query", query); // 返回查询的语句到界面上
        return "search";
    }

}
