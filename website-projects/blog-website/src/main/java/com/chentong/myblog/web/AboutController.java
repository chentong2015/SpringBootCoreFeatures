package com.chentong.myblog.web;

import com.chentong.myblog.model.About;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(Model model){
        About myself = new About();
        myself.setTags(new ArrayList<>(Arrays.asList("编程", "旅行", "思考人生", "运动", "音乐电影")));
        myself.setCodes(new ArrayList<>(Arrays.asList("Java", "Swift", "JavaScript", "Angular", "Node.js", "C#", "C++", "C语言", "Android", "iOS", "Linux", "PHP", "Spring Boot", "Unity 3D", "AR & VR")));
        model.addAttribute("about", myself);
        return "about";
    }

}
