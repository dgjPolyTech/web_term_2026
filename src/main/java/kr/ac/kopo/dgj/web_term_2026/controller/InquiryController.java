package kr.ac.kopo.dgj.web_term_2026.controller;

import kr.ac.kopo.dgj.web_term_2026.InquiryService.InquiryService;
import kr.ac.kopo.dgj.web_term_2026.ReplyService.ReplyService;
import kr.ac.kopo.dgj.web_term_2026.domain.Inquiry;
import kr.ac.kopo.dgj.web_term_2026.domain.Reply;
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
    private final ReplyService replyService;

    @Autowired
    public InquiryController(InquiryService inquiryService, ReplyService replyService) {
        this.inquiryService = inquiryService;
        this.replyService = replyService;
    }

    // 로그인 페이지: http://localhost:8080/login
    @GetMapping("/login")
    public String login() {
        return "view_login";
    }

    // 메인 페이지: http://localhost:8080/main
    @GetMapping("/main")
    public String viewMain(
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {
        
        List<Inquiry> inquiryList;
        if (searchType != null && keyword != null && !keyword.trim().isEmpty()) {
            inquiryList = inquiryService.searchInquiryList(searchType, keyword);
        } else {
            inquiryList = inquiryService.getAllInquiryList();
        }
        
        model.addAttribute("inquiryList", inquiryList);
        return "view_main";
    }

    // view_detail 페이지: http://localhost:8080/dgjs/detail?id=ISIN-0001
    @GetMapping("/detail")
    public String viewDetail(@RequestParam(value = "id", required = false) String id, Model model) {
        if (id != null) {
            Inquiry inquiry = inquiryService.getInquiryId(id);
            model.addAttribute("inquiry", inquiry);

            // 해당 문의에 연계된 답변 조회 (없으면 null)
            Reply reply = replyService.getReplyByInquiryId(id);
            model.addAttribute("reply", reply);
        }
        return "view_detail";
    }

    // 문의 등록/답변 등록 폼 페이지: GET /input (또는 /input?id=ISIN-0001)
    @GetMapping("/input")
    public String viewInput(@RequestParam(value = "id", required = false) String id, Model model) {
        if (id != null) {
            // view_detail에서 답변 등록/수정 버튼으로 진입한 경우
            Inquiry inquiry = inquiryService.getInquiryId(id);
            model.addAttribute("inquiry", inquiry);

            Reply reply = replyService.getReplyByInquiryId(id);
            model.addAttribute("reply", reply); // 없으면 null (신규 등록), 있으면 기존 답변 (수정)
        }
        return "view_input";
    }

    // 문의 등록 처리: http://localhost:8081/input (POST)
    @PostMapping("/input")
    public String submitInput(@ModelAttribute Inquiry inquiry) throws java.io.IOException {
        // 파일 업로드 처리
        if (inquiry.getAttachedFile() != null && !inquiry.getAttachedFile().isEmpty()) {
            org.springframework.web.multipart.MultipartFile file = inquiry.getAttachedFile();
            String fullPath = "D:/upload/" + file.getOriginalFilename();
            file.transferTo(new java.io.File(fullPath));
            inquiry.setFileName(file.getOriginalFilename());
        }

        // 새 문의 등록을 위한 메타데이터 임의 세팅 (테스트용)
        if (inquiry.getInquiryId() == null || inquiry.getInquiryId().isEmpty()) {
            inquiry.setInquiryId("ISIN-" + String.format("%04d", (int)(Math.random() * 9000) + 1000));
            inquiry.setRegistrationDate(java.time.LocalDateTime.now());
            inquiry.setStatus(kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus.WAITING);
            inquiryService.setNewInquiry(inquiry);
        }

        return "redirect:/main";
    }
    // 상태 변경 (Admin)
    @PostMapping("/inquiry/status")
    public String updateStatus(@RequestParam("inquiryId") String inquiryId, @RequestParam("status") kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus status) {
        inquiryService.updateInquiryStatus(inquiryId, status);
        return "redirect:/detail?id=" + inquiryId;
    }

    // 문의 수정 처리: POST /inquiry/update
    @PostMapping("/inquiry/update")
    public String updateInquiry(@ModelAttribute Inquiry inquiry, 
                                @RequestParam(value = "removeFile", defaultValue = "false") boolean removeFile) throws java.io.IOException {
        if (removeFile) {
            inquiry.setFileName(null);
        } else if (inquiry.getAttachedFile() != null && !inquiry.getAttachedFile().isEmpty()) {
            org.springframework.web.multipart.MultipartFile file = inquiry.getAttachedFile();
            String fullPath = "D:/upload/" + file.getOriginalFilename();
            file.transferTo(new java.io.File(fullPath));
            inquiry.setFileName(file.getOriginalFilename());
        }
        
        inquiryService.updateInquiry(inquiry);
        
        return "redirect:/detail?id=" + inquiry.getInquiryId();
    }

    // 문의 삭제 처리: POST /inquiry/delete
    @PostMapping("/inquiry/delete")
    public String deleteInquiry(@RequestParam("inquiryId") String inquiryId) {
        inquiryService.deleteInquiry(inquiryId);
        return "redirect:/main";
    }
}
