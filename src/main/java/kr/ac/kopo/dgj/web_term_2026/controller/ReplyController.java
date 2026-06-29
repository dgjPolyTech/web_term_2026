package kr.ac.kopo.dgj.web_term_2026.controller;

import kr.ac.kopo.dgj.web_term_2026.InquiryService.InquiryService;
import kr.ac.kopo.dgj.web_term_2026.ReplyService.ReplyService;
import kr.ac.kopo.dgj.web_term_2026.domain.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class ReplyController {

    private final ReplyService replyService;
    private final InquiryService inquiryService;

    @Autowired
    public ReplyController(ReplyService replyService, InquiryService inquiryService) {
        this.replyService = replyService;
        this.inquiryService = inquiryService;
    }

    // 답변 등록: POST /reply
    @PostMapping("/reply")
    public String submitReply(@ModelAttribute Reply reply, @RequestParam(value = "status", required = false) kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus status) throws java.io.IOException {
        if (reply.getAttachedFile() != null && !reply.getAttachedFile().isEmpty()) {
            org.springframework.web.multipart.MultipartFile file = reply.getAttachedFile();
            String fullPath = "D:/upload/" + file.getOriginalFilename();
            file.transferTo(new java.io.File(fullPath));
            reply.setFileName(file.getOriginalFilename());
        }
        reply.setReplyDate(LocalDateTime.now()); // 등록일시 설정
        // replyId 자동 생성 (간단히 타임스탬프 기반)
        reply.setReplyId("RPID-" + System.currentTimeMillis());
        replyService.setNewReply(reply);
        if (status != null) {
            inquiryService.updateInquiryStatus(reply.getInquiryId(), status);
        } else {
            inquiryService.updateInquiryStatus(reply.getInquiryId(), kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus.DONE);
        }
        return "redirect:/detail?id=" + reply.getInquiryId();
    }

    // 답변 수정: POST /reply/update
    @PostMapping("/reply/update")
    public String updateReply(@ModelAttribute Reply reply, 
                              @RequestParam(value = "status", required = false) kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus status,
                              @RequestParam(value = "removeFile", defaultValue = "false") boolean removeFile) throws java.io.IOException {
        if (removeFile) {
            reply.setFileName(null);
        } else if (reply.getAttachedFile() != null && !reply.getAttachedFile().isEmpty()) {
            org.springframework.web.multipart.MultipartFile file = reply.getAttachedFile();
            String fullPath = "D:/upload/" + file.getOriginalFilename();
            file.transferTo(new java.io.File(fullPath));
            reply.setFileName(file.getOriginalFilename());
        }
        
        replyService.updateReply(reply);
        if (status != null) {
            inquiryService.updateInquiryStatus(reply.getInquiryId(), status);
        } else {
            inquiryService.updateInquiryStatus(reply.getInquiryId(), kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus.DONE);
        }
        return "redirect:/detail?id=" + reply.getInquiryId();
    }
}
