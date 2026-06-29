package kr.ac.kopo.dgj.web_term_2026.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reply {
    private String replyId;          // 답변 고유 ID (예: RPID-0001)
    private String inquiryId;        // 연결된 문의 ID (외래키 역할)
    private String manager;          // 담당자명
    private String replyContents;    // 답변 내용
    private LocalDateTime replyDate; // 처리(등록)일시
    
    private String fileName;         // 첨부파일 이름
    private org.springframework.web.multipart.MultipartFile attachedFile; // 파일 업로드용
}
