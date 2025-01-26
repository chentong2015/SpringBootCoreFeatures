package com.chentong.myblog.web.admin;

import com.chentong.myblog.model.Tag;
import com.chentong.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    @PostMapping("/tags")  //使用RedirectAttributes定向信息到前端的界面
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){

        Tag tagTemp = tagService.getTagByName(tag.getName());
        if(tagTemp != null) {
            result.rejectValue("name", "nameError", "标签的名称重复!");
        }
        if(result.hasErrors()){
            return "admin/tags-edit";
        }
        Tag t =  tagService.saveTag(tag);
        if(t == null){
            attributes.addFlashAttribute("message", "添加标签，操作失败");
        } else {
            attributes.addFlashAttribute("message", "添加标签，操作成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String postEdit(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Tag tagTemp = tagService.getTagByName(tag.getName());
        if(tagTemp != null) {
            result.rejectValue("name", "nameError", "标签的名称重复!");
        }
        if(result.hasErrors()){
            return "admin/tags-edit";
        }
        Tag t =  tagService.updateTag(id, tag);
        if(t == null){
            attributes.addFlashAttribute("message", "更新标签，操作失败");
        } else {
            attributes.addFlashAttribute("message", "更新标签，操作成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/edit")
    public String typesEdit(Model model) {
        model.addAttribute("tag", new Tag());
        // Show the edit type page
        return "admin/tags-edit";
    }

    @GetMapping("/tags/{id}/edit")
    public String typesChange(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-edit";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

}
