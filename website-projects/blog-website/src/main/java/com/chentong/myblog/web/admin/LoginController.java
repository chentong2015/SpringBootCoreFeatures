package com.chentong.myblog.web.admin;

import com.chentong.myblog.model.User;
import com.chentong.myblog.service.UserService;
import com.chentong.myblog.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/** Controller
 * 定义的主目录是在admin下的 后台页面控制的部分
 * Login: chentong
 * Password: TChong15
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserServiceImpl userService; //应用操作User表格的Service服务; 使用对应的接口的实现类

    @GetMapping("")
    public String loginPage() {
        //默认访问的页面路径admin/login
        return "admin/login";
    }

    @PostMapping("/login")  // 使用POST方式提交，并通过RequestParam获取到传入的参数; 只有该调条件下才去判断错误的信息message
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        // 用户正确登陆，创建用的Session, 不要将用户的密码拿到页面去 ===> 显示该用户名称在页面的右上角
        if(user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        } else {
            // RedirectAttributes将controller的控制的信息发到前端的界面使用 !!!
            attributes.addFlashAttribute("message", "用户名或者密码错误");
            System.out.println(" Connection Failed !!");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
