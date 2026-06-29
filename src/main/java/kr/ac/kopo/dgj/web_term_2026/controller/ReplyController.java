package kr.ac.kopo.dgj.web_term_2026.controller;

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

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    // 답변 등록: POST /reply
    @PostMapping("/reply")
    public String submitReply(@ModelAttribute Reply reply) throws java.io.IOException {
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
        return "redirect:/detail?id=" + reply.getInquiryId();
    }

    // 답변 수정: POST /reply/update
    @PostMapping("/reply/update")
    public String updateReply(@ModelAttribute Reply reply) throws java.io.IOException {
        if (reply.getAttachedFile() != null && !reply.getAttachedFile().isEmpty()) {
            org.springframework.web.multipart.MultipartFile file = reply.getAttachedFile();
            String fullPath = "D:/upload/" + file.getOriginalFilename();
            file.transferTo(new java.io.File(fullPath));
            reply.setFileName(file.getOriginalFilename());
        }
        replyService.updateReply(reply);
        return "redirect:/detail?id=" + reply.getInquiryId();
    }
}
