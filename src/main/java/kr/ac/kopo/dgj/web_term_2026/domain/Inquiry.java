package kr.ac.kopo.dgj.web_term_2026.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Inquiry {
    private String title;
    private String requester;
    private String organization;
    private String manager;
    private String contents;

    private String fileName;
    private MultipartFile attachedFile;
}
