package com.chentong.myblog.web.admin;

import com.chentong.myblog.model.Type;
import com.chentong.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 保持GetMapping的路径和函数的名称一致
     * 前端页面的构造的参数会自动封装到pageable对象中
     * web层次实现分页查询功能
     */
    /**
     *  当访问到改路径的页面时，从数据库中将数据以分页的方式那出来 ( Define default : 分页的大小和排序方式)，送到页面的model中去使用
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page", typeService.listType(pageable)); // 返回pageable的数据对象page, 在前端界面进行解析page
        return "admin/types";
    }

    /**
     * 同一个路径下可以有多个的访问方式 GET & POST
     * 从页面获取传递的name对象(Table中的列的名称)，到控制器接收
     * 提示新增类型是否成功
     * @Valid 要进行后端验证的对象
     * @BindingResult 接受验证之后的结果
     */
    @PostMapping("/types")  //使用RedirectAttributes定向信息到前端的界面
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        /**
         * 验证是否添加同样的信息名称
         */
        Type typeTemp = typeService.getTypeByName(type.getName());
        if(typeTemp != null) {
            // name 变量的名称是前端获取的对象名称
            result.rejectValue("name", "nameError", "类型的名称重复!");
        }
        if(result.hasErrors()){
            return "admin/types-edit";
        }
        Type t =  typeService.saveType(type);
        if(t == null){
            attributes.addFlashAttribute("message", "添加类型，操作失败");
        } else {
            attributes.addFlashAttribute("message", "添加类型，操作成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")  //使用RedirectAttributes定向信息到前端的界面
    public String postEdit(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Type typeTemp = typeService.getTypeByName(type.getName());
        if(typeTemp != null) {
            // name 变量的名称是前端获取的对象名称
            result.rejectValue("name", "nameError", "类型的名称重复!");
        }
        if(result.hasErrors()){
            return "admin/types-edit";
        }
        Type t =  typeService.updateType(id, type);
        if(t == null){
            attributes.addFlashAttribute("message", "更新类型，操作失败");
        } else {
            attributes.addFlashAttribute("message", "更新类型，操作成功");
        }
        return "redirect:/admin/types";
    }

    // 直接进入编辑新增的界面的时候，不创建新的对象，是前端的对象type为空，并且是id为空，调用post函数进行新增
    @GetMapping("/types/edit")
    public String typesEdit(Model model) {
        model.addAttribute("type", new Type()); //将该对象传到前端去使用，绑定输入的作用域
        // Show the edit type page
        return "admin/types-edit";
    }

    @GetMapping("/types/{id}/edit") //接到从url中传入的id，并将Model的对象提到前端使用, 将获取的Type对象送到前端使用id和name的信息
    public String typesChange(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-edit";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

}
