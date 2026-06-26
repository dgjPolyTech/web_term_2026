package kr.ac.kopo.dgj.web_term_2026.repository;

import kr.ac.kopo.dgj.web_term_2026.domain.Inquiry;

import java.util.ArrayList;
import java.util.List;

public class InquiryRepositoryImpl implements InquiryRepository{
    private List<Inquiry> ListOfInquirys = new ArrayList<>();

    // 생성자 선언 단계에서 기본 문의들 가져오기
    public InquiryRepositoryImpl() {
        Inquiry inquiry1 = new Inquiry();
        inquiry1.setInquiryId("ISIN-0001");
        inquiry1.setTitle("도로 파손 복구 요청");
        inquiry1.setRequester("홍길동");
        inquiry1.setOrganization("서울시청");
        inquiry1.setManager("김철수");
        inquiry1.setContents("강남대로 123 앞 도로가 파손되어 불편합니다.");
        inquiry1.setRegistrationDate(java.time.LocalDateTime.now());
        inquiry1.setStatus(kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus.WAITING);
        ListOfInquirys.add(inquiry1);

        Inquiry inquiry2 = new Inquiry();
        inquiry2.setInquiryId("ISIN-0002");
        inquiry2.setTitle("가로등 고장 신고");
        inquiry2.setRequester("이순신");
        inquiry2.setOrganization("강남구청");
        inquiry2.setManager("박영희");
        inquiry2.setContents("역삼동 456 골목 가로등이 며칠째 안 켜집니다.");
        inquiry2.setRegistrationDate(java.time.LocalDateTime.now().minusDays(1));
        inquiry2.setStatus(kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus.IN_PROGRESS);
        ListOfInquirys.add(inquiry2);

        Inquiry inquiry3 = new Inquiry();
        inquiry3.setInquiryId("ISIN-0003");
        inquiry3.setTitle("불법 주차 단속 요청");
        inquiry3.setRequester("강감찬");
        inquiry3.setOrganization("서초구청");
        inquiry3.setManager("이민수");
        inquiry3.setContents("서초대로 789 앞에 불법 주차된 차량들이 많습니다.");
        inquiry3.setRegistrationDate(java.time.LocalDateTime.now().minusDays(2));
        inquiry3.setStatus(kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus.DONE);
        ListOfInquirys.add(inquiry3);
    }

    @Override
    public List<Inquiry> getAllInquiryList() {
        return ListOfInquirys;
    }

    @Override
    public Inquiry getInquiryId(String inquiryId) {
        // inquiry 객체를 null 값으로 선언해둔다.
        Inquiry inquiry = null;

        // inquiry 목록을 인핸스드 for문으로 하나하나 찾는다.
        for (Inquiry searchInquiry : ListOfInquirys) {
            if (searchInquiry != null && searchInquiry.getInquiryId() != null && searchInquiry.getInquiryId().equals(inquiryId)) {
                inquiry = searchInquiry; // inquiry를 찾았으면, 위에서 선언한 객체에 넣는다.
                break; // break로 빠져나간다.
            }
        }

        if (inquiry == null) {
            // inquiry를 찾지 못했으면, 목록에 없다는 소리이므로 강제로 오류를 발생시킨다.
            throw new IllegalArgumentException("문의ID가 " + inquiryId + "인 문의를 찾을 수가 없습니다.");
        }

        return inquiry;
    }

    @Override
    public List<Inquiry> getInquiryListByOrganization(String organization) {
        List<Inquiry> inquiriesByOrganization = new ArrayList<>();

        for (Inquiry searchInquiry : ListOfInquirys) {
            if (organization.equalsIgnoreCase(searchInquiry.getOrganization())) {
                inquiriesByOrganization.add(searchInquiry);
            }
        }

        return inquiriesByOrganization;
    }

    @Override
    public void setNewInquiry(Inquiry inquiry) {
        ListOfInquirys.add(inquiry);
    }

}
