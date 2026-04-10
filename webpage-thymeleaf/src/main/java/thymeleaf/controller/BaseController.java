package thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    // TODO. 重定向到static静态资源页面
    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/static.html";
    }

    // TODO. 重定向到templates模板页面
    @GetMapping(value = "setup")
    public String setup() {
        System.out.println("Process the input for playing ...");
        return "redirect:/home";
    }

    @GetMapping("home")
    public String home(Model model) {
        System.out.println("home method called ...");
        model.addAttribute("value", "About page");
        model.addAttribute("resultMessage", "This is the result message");
        return "home";
    }
}
