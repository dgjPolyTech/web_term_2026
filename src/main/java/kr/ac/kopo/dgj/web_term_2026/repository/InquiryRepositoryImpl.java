package kr.ac.kopo.dgj.web_term_2026.repository;

import kr.ac.kopo.dgj.web_term_2026.domain.Inquiry;
import kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InquiryRepositoryImpl implements InquiryRepository{
    private List<Inquiry> ListOfInquirys = new ArrayList<>();

    // 생성자 선언 단계에서 기본 문의들 가져오기
    public InquiryRepositoryImpl() {
        Inquiry inquiry1 = new Inquiry();
        inquiry1.setInquiryId("ISIN-0001");
        inquiry1.setTitle("도로 파손 복구 요청");
        inquiry1.setRequester("홍길동");
        inquiry1.setOrganization("서울시청");
        inquiry1.setManager("김담당"); // 김담당으로 수정
        inquiry1.setContents("강남대로 123 앞 도로가 파손되어 불편합니다.");
        inquiry1.setRegistrationDate(java.time.LocalDateTime.now());
        inquiry1.setStatus(kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus.WAITING);
        ListOfInquirys.add(inquiry1);

        Inquiry inquiry2 = new Inquiry();
        inquiry2.setInquiryId("ISIN-0002");
        inquiry2.setTitle("가로등 고장 신고");
        inquiry2.setRequester("이순신");
        inquiry2.setOrganization("강남구청");
        inquiry2.setManager("이담당"); // 이담당으로 수정
        inquiry2.setContents("역삼동 456 골목 가로등이 며칠째 안 켜집니다.");
        inquiry2.setRegistrationDate(java.time.LocalDateTime.now().minusDays(1));
        inquiry2.setStatus(kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus.IN_PROGRESS);
        ListOfInquirys.add(inquiry2);

        Inquiry inquiry3 = new Inquiry();
        inquiry3.setInquiryId("ISIN-0003");
        inquiry3.setTitle("불법 주차 단속 요청");
        inquiry3.setRequester("강감찬");
        inquiry3.setOrganization("서초구청");
        inquiry3.setManager("임담당"); // 임담당으로 수정
        inquiry3.setContents("서초대로 789 앞에 불법 주차된 차량들이 많습니다.");
        inquiry3.setRegistrationDate(java.time.LocalDateTime.now().minusDays(2));
        inquiry3.setStatus(kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus.DONE);
        ListOfInquirys.add(inquiry3);
    }

    @Override
    public List<Inquiry> getAllInquiryList() {
        List<Inquiry> sortedList = new ArrayList<>(ListOfInquirys);
        sortedList.sort((i1, i2) -> {
            if (i1.getRegistrationDate() == null || i2.getRegistrationDate() == null) return 0;
            return i2.getRegistrationDate().compareTo(i1.getRegistrationDate());
        });
        return sortedList;
    }

    @Override
    public List<Inquiry> searchInquiryList(String searchType, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllInquiryList();
        }
        
        List<Inquiry> searchResult = new ArrayList<>();
        String lowerKeyword = keyword.trim().toLowerCase();

        for (Inquiry inquiry : ListOfInquirys) {
            boolean match = false;
            switch (searchType) {
                case "title":
                    if (inquiry.getTitle() != null && inquiry.getTitle().toLowerCase().contains(lowerKeyword)) {
                        match = true;
                    }
                    break;
                case "organization":
                    if (inquiry.getOrganization() != null && inquiry.getOrganization().toLowerCase().contains(lowerKeyword)) {
                        match = true;
                    }
                    break;
                case "requester":
                    if (inquiry.getRequester() != null && inquiry.getRequester().toLowerCase().contains(lowerKeyword)) {
                        match = true;
                    }
                    break;
                case "status":
                    String statusName = "";
                    if (inquiry.getStatus() == InquiryStatus.WAITING) statusName = "처리 대기";
                    else if (inquiry.getStatus() == InquiryStatus.IN_PROGRESS) statusName = "진행 중";
                    else if (inquiry.getStatus() == InquiryStatus.DONE) statusName = "처리 완료";
                    
                    if (statusName.contains(lowerKeyword) || statusName.replace(" ", "").contains(lowerKeyword.replace(" ", ""))) {
                        match = true;
                    }
                    break;
            }
            if (match) {
                searchResult.add(inquiry);
            }
        }
        
        searchResult.sort((i1, i2) -> {
            if (i1.getRegistrationDate() == null || i2.getRegistrationDate() == null) return 0;
            return i2.getRegistrationDate().compareTo(i1.getRegistrationDate());
        });
        return searchResult;
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

    @Override
    public void updateInquiryStatus(String inquiryId, InquiryStatus status) {
        Inquiry inquiry = getInquiryId(inquiryId);
        if (inquiry != null) {
            inquiry.setStatus(status);
        }
    }

    @Override
    public void updateInquiry(Inquiry updated) {
        Inquiry existing = getInquiryId(updated.getInquiryId());
        if (existing != null) {
            existing.setTitle(updated.getTitle());
            existing.setOrganization(updated.getOrganization());
            existing.setContents(updated.getContents());
            existing.setFileName(updated.getFileName());
            // requester and registrationDate usually remain unchanged.
        }
    }

    @Override
    public void deleteInquiry(String inquiryId) {
        ListOfInquirys.removeIf(inquiry -> inquiry != null && inquiryId.equals(inquiry.getInquiryId()));
    }
}
