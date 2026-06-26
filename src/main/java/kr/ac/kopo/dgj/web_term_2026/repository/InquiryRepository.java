package kr.ac.kopo.dgj.web_term_2026.repository;

import kr.ac.kopo.dgj.web_term_2026.domain.Inquiry;

import java.util.List;

public interface InquiryRepository {

    List<Inquiry> getAllInquiryList(); // 문의 리스트

    Inquiry getInquiryId(String inquiryId); // 특정 문의 가져오기

    List<Inquiry> getInquiryListByOrganization(String organization); // 특정 기관의 문의 목록 가져오기

    void setNewBook(Book book);
}
