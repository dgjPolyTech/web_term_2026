package kr.ac.kopo.dgj.web_term_2026;

import kr.ac.kopo.dgj.web_term_2026.domain.Inquiry;
import kr.ac.kopo.dgj.web_term_2026.domain.InquiryStatus;
import kr.ac.kopo.dgj.web_term_2026.repository.InquiryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WebTerm2026IntegrationTestBed {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InquiryRepository inquiryRepository;

    @Test
    @DisplayName("1. [USER] 사용자가 새로운 문의를 등록할 수 있다.")
    @WithMockUser(username = "User", roles = {"USER"})
    public void testUserCanCreateInquiry() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/input")
                        .with(csrf())
                        .param("title", "테스트 베드 문의")
                        .param("organization", "서울시청")
                        .param("manager", "김담당")
                        .param("contents", "이것은 테스트 문의 내용입니다."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"));

        // Repository에서 생성되었는지 확인
        long count = inquiryRepository.getAllInquiryList().stream()
                .filter(i -> "테스트 베드 문의".equals(i.getTitle()))
                .count();
        assertTrue(count > 0, "문의가 정상적으로 등록되어야 합니다.");
    }

    @Test
    @DisplayName("2. [ADMIN] 담당 관리자(김담당)는 할당된 문의에 답변을 등록할 수 있다.")
    @WithMockUser(username = "Admin", roles = {"ADMIN"})
    public void testAssignedAdminCanReplyWithFile() throws Exception {
        // 기존 김담당으로 등록된 문의 (ISIN-0001)에 대해 테스트
        Inquiry target = inquiryRepository.getInquiryId("ISIN-0001");
        assertNotNull(target);
        assertEquals("김담당", target.getManager(), "ISIN-0001의 담당자는 김담당이어야 합니다.");

        MockMultipartFile file = new MockMultipartFile("attachedFile", "test.jpg", "image/jpeg", "test image content".getBytes());

        mockMvc.perform(multipart("/reply")
                        .file(file)
                        .with(csrf())
                        .param("inquiryId", "ISIN-0001")
                        .param("manager", "김담당")
                        .param("replyContents", "김담당이 작성하는 테스트 답변입니다."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/detail?id=ISIN-0001"));
    }

    @Test
    @DisplayName("3. [ADMIN] 담당자가 아닌 관리자는 문의를 수정하거나 답변을 등록할 수 없도록 UI 렌더링 시 권한이 제한된다.")
    @WithMockUser(username = "Admin", roles = {"ADMIN"})
    public void testUnassignedAdminCannotSeeSubmitButton() throws Exception {
        // 이담당으로 등록된 문의 (ISIN-0002)에 대해 접근 시도
        Inquiry target = inquiryRepository.getInquiryId("ISIN-0002");
        assertNotNull(target);
        assertEquals("이담당", target.getManager(), "ISIN-0002의 담당자는 이담당이어야 합니다.");

        // UI상에서 등록 버튼이 숨겨져 있는지, textarea가 readonly인지 확인
        mockMvc.perform(get("/input").param("id", "ISIN-0002"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("readonly=\"readonly\"")))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("<button type=\"submit\" class=\"btn btn-submit\">등록</button>"))));
    }

    @Test
    @DisplayName("4. [USER] 메인 페이지 목록 조회 및 검색이 정상 동작해야 한다.")
    @WithMockUser(username = "User", roles = {"USER"})
    public void testSearchAndList() throws Exception {
        mockMvc.perform(get("/main")
                        .param("searchType", "title")
                        .param("keyword", "파손"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("도로 파손 복구 요청")));
    }
}
