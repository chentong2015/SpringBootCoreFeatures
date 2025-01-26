package com.chentong.myblog.web;

import com.chentong.myblog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchivesController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/archives")
    public String archives(Model model){
        // 返回的数据是 Map<String, List<Blog>>
        model.addAttribute("archivesMap", adminService.listArchivesBlog());
        model.addAttribute("count", adminService.countBlogs());
        return "archives";
    }

}

