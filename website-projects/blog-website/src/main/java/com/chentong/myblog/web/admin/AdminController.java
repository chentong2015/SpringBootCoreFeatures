package com.chentong.myblog.web.admin;

import com.chentong.myblog.model.Blog;
import com.chentong.myblog.query.BlogQuery;
import com.chentong.myblog.model.User;
import com.chentong.myblog.service.AdminService;
import com.chentong.myblog.service.TagService;
import com.chentong.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 在加载Get的时候便调用的方法
     * 将后端查询的数据放到model的模型中, 在前端使用model中的数据
     * 此处可以限制一页显示的博客的数目size
     */
    @GetMapping("/admin")
    public String admin(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page", adminService.listBlog(pageable, blog));
        model.addAttribute("types", typeService.listType()); // 从TypeService中获取到所有的类型，用于blog界面的加载 ==> list Type
        return "admin/admin";
    }

    /**
     * 提交保存博客的操作，使用Post方法访问到界面 : 区别是新增的还是更新的博客
     */
    @PostMapping("/admin")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId())); //根据前端界面来拿到对应的typeID ；name="type.id"
        blog.setListTags(tagService.listTag(blog.getTagIds()));
        Blog blog1;
        if(blog.getId() == null) {
            blog1 = adminService.saveBlog(blog);
        } else {
            blog1 = adminService.updateBlog(blog.getId(), blog);
        }
        if(blog1 == null){
            attributes.addFlashAttribute("message", "操作失败"); // 该message将会拿到前端去做判断
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/admin";
    }

    // 使用button搜索，post页面的访问方式
    @PostMapping("/admin/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page", adminService.listBlog(pageable, blog));
        return "admin/admin :: blogList"; // 返回的页面局部的一个片段fragment，实现页面局部的刷新
    }

    // 新增博客的链接 new Blog()
    @GetMapping("/admin/edit")
    public String edit(Model model){
        // 方便于修改博客的页面进行共用 : 初始化界面的3个信息
        model.addAttribute("blog", new Blog());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return "admin/admin-edit";
    }

    // 编辑博客的界面 传参id ==> getBlog(id)
    @GetMapping("/admin/{id}/edit")
    public String editblog(@PathVariable Long id, Model model){
        // 方便于修改博客的页面进行共用 : 初始化界面的3个信息
        Blog blog = adminService.getBlog(id);
        blog.init(); // 将blog的搜所有的tag id处理成字符串 1，2，3
        model.addAttribute("blog",blog);
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return "admin/admin-edit";
    }

    // 直接根据博客的id进行删除即可
    @GetMapping("/admin/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        adminService.deleteBlog(id);
        attributes.addFlashAttribute("message", "操作成功");
        return "redirect:/admin/admin";
    }

    @GetMapping("/admin/types")
    public String types(){ return "admin/types"; }

    @GetMapping("/admin/tags")
    public String tags(){ return "admin/tags"; }

}
