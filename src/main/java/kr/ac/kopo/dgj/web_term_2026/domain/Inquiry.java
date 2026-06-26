package kr.ac.kopo.dgj.web_term_2026.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class Inquiry {
//    @Pattern(regexp = "^ISIN-\\d{4}$", message = "문의 번호는 ISIN-0001 형식이어야 합니다.")
    private String inquiryId;

    private String title; // 문의 제목
    private String requester; // 신청자 명
    private String organization; // 기관 명
    private String manager; // 담당자 명
    private String contents; // 내용

    private String fileName; // 파일 명
    private MultipartFile attachedFile; // 파일 첨부 관련

    private LocalDateTime registrationDate; // 등록일
    private InquiryStatus status; // 처리 상태(처리 대기, 진행 중, 처리 완료)

}
