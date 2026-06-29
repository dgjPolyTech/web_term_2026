package kr.ac.kopo.dgj.web_term_2026.InquiryService;

import kr.ac.kopo.dgj.web_term_2026.domain.Inquiry;
import kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus;
import kr.ac.kopo.dgj.web_term_2026.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InquiryServiceImpl implements InquiryService{

    private final InquiryRepository inquiryRepository;

    @Autowired
    public InquiryServiceImpl(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @Override
    public List<Inquiry> getAllInquiryList() {
        return inquiryRepository.getAllInquiryList();
    }

    @Override
    public List<Inquiry> searchInquiryList(String searchType, String keyword) {
        return inquiryRepository.searchInquiryList(searchType, keyword);
    }

    @Override
    public Inquiry getInquiryId(String inquiryId) {
        return inquiryRepository.getInquiryId(inquiryId);
    }

    @Override
    public List<Inquiry> getInquiryListByOrganization(String organization) {
        return inquiryRepository.getInquiryListByOrganization(organization);
    }

    @Override
    public void setNewInquiry(Inquiry inquiry) {
        inquiryRepository.setNewInquiry(inquiry);
    }

    @Override
    public void updateInquiryStatus(String inquiryId, InquiryStatus status) {
        inquiryRepository.updateInquiryStatus(inquiryId, status);
    }
}
