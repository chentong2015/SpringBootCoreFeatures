package thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import thymeleaf.service.InternationalService;

@Controller("/inter")
public class InternationalController {

    private final InternationalService internationalizationService;

    public InternationalController(InternationalService internationalizationService) {
        this.internationalizationService = internationalizationService;
    }

    // TODO. 找到Locale当地翻译的语言信息
    @GetMapping("game")
    public String about(Model model) {
        String localeMessage = internationalizationService.getGameMessage();
        model.addAttribute("game", localeMessage);
        return "game";
    }
}
