package kr.ac.kopo.dgj.web_term_2026.controller;

import kr.ac.kopo.dgj.web_term_2026.InquiryService.InquiryService;
import kr.ac.kopo.dgj.web_term_2026.domain.Inquiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class InquiryController {

    private final InquiryService inquiryService;

    @Autowired
    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    // 로그인 페이지: http://localhost:8080/login
    @GetMapping("/login")
    public String login() {
        return "view_login";
    }

    // 메인 페이지: http://localhost:8080/main
    @GetMapping("/main")
    public String viewMain(Model model) {
        List<Inquiry> inquiryList = inquiryService.getAllInquiryList();
        model.addAttribute("inquiryList", inquiryList);
        return "view_main";
    }

    // view_detail 페이지: http://localhost:8081/view_detail
    @GetMapping("/detail")
    public String viewDetail(@RequestParam(value = "id", required = false) String id, Model model) {
        if (id != null) {
            Inquiry inquiry = inquiryService.getInquiryId(id);
            model.addAttribute("inquiry", inquiry);
        }
        return "view_detail";
    }

    // 문의 등록 폼 페이지: http://localhost:8081/view_input
    @GetMapping("/input")
    public String viewInput() {
        return "view_input";
    }

    // 문의 등록 처리: http://localhost:8081/input (POST)
    @PostMapping("/input")
    public String submitInput(@ModelAttribute Inquiry inquiry) {
        inquiryService.setNewInquiry(inquiry);
        return "redirect:/main";
    }
}
