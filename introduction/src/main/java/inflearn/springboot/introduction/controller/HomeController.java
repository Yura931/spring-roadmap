package inflearn.springboot.introduction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 컨트롤러 경로 우선순위, 정적경로 무시
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
