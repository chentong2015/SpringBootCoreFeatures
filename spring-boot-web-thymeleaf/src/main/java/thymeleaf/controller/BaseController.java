package thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BaseController {

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/home.html";
    }

    @GetMapping("home")
    public String home(Model model) {
        System.out.println("home method called ...");
        model.addAttribute("value", "About page");
        model.addAttribute("resultMessage", "This is the result message");
        return "home";
    }

    @PostMapping(value = "play")
    public String play(@RequestParam int input) {
        System.out.println("Process the input for playing ...");
        return "redirect:/home";
    }
}
