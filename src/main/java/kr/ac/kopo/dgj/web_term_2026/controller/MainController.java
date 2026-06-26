package kr.ac.kopo.dgj.web_term_2026.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    // 로그인 페이지: http://localhost:8080/login
    @GetMapping("/login")
    public String login() {
        return "view_login";
    }

    // 메인 페이지: http://localhost:8080/
    @GetMapping("/main")
    public String viewMain() {
        return "view_main";
    }

    // view_detail 페이지: http://localhost:8081/view_detail
    @GetMapping("/detail")
    public String viewDetail() {
        return "view_detail";
    }

    // view_detail 페이지: http://localhost:8081/view_detail
    @GetMapping("/input")
    public String viewInput() {
        return "view_input";
    }
}
