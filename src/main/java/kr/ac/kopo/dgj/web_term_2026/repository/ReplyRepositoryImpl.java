package kr.ac.kopo.dgj.web_term_2026.repository;

import kr.ac.kopo.dgj.web_term_2026.domain.Reply;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReplyRepositoryImpl implements ReplyRepository {

    private List<Reply> listOfReplies = new ArrayList<>();

    // 생성자 선언 단계에서 기본 답변 더미 데이터 생성
    public ReplyRepositoryImpl() {
        // ISIN-0002 (가로등 고장 신고) 에 대한 답변
        Reply reply1 = new Reply();
        reply1.setReplyId("RPID-0001");
        reply1.setInquiryId("ISIN-0002");
        reply1.setManager("박영희");
        reply1.setReplyContents("안녕하세요. 담당자 박영희입니다.\n\n" +
                "신고해주신 역삼동 456 골목 가로등 고장 건에 대해 현장 점검을 완료하였습니다.\n" +
                "전구 노후화로 인한 단순 소모품 교체가 필요한 상황으로 확인되었으며,\n" +
                "금일 오후 유지보수 인력을 파견하여 교체 작업을 진행 중에 있습니다.\n\n" +
                "불편을 드려 죄송합니다. 추가 문의가 있으시면 언제든 연락 바랍니다.");
        reply1.setReplyDate(LocalDateTime.now().minusHours(6));
        listOfReplies.add(reply1);

        // ISIN-0003 (불법 주차 단속 요청) 에 대한 답변
        Reply reply2 = new Reply();
        reply2.setReplyId("RPID-0002");
        reply2.setInquiryId("ISIN-0003");
        reply2.setManager("이민수");
        reply2.setReplyContents("안녕하세요. 담당자 이민수입니다.\n\n" +
                "신고해주신 서초대로 789 앞 불법 주차 단속 요청 건에 대해 조치 완료하였습니다.\n" +
                "어제 오전 단속 인력을 현장에 파견하여 불법 주차 차량 총 7대에 과태료를 부과하였으며,\n" +
                "해당 구역에 주정차 금지 안내 표지판을 추가 설치하였습니다.\n\n" +
                "향후 재발 방지를 위해 주기적인 순찰도 강화할 예정입니다. 감사합니다.");
        reply2.setReplyDate(LocalDateTime.now().minusDays(1).minusHours(2));
        listOfReplies.add(reply2);

        // ISIN-0001 (도로 파손 복구 요청) → 답변 없음 (처리 대기 상태)
    }

    @Override
    public Reply getReplyByInquiryId(String inquiryId) {
        for (Reply reply : listOfReplies) {
            if (reply.getInquiryId().equals(inquiryId)) {
                return reply;
            }
        }
        return null; // 답변이 없으면 null 반환
    }

    @Override
    public void setNewReply(Reply reply) {
        listOfReplies.add(reply);
    }

    @Override
    public void updateReply(Reply updatedReply) {
        for (int i = 0; i < listOfReplies.size(); i++) {
            if (listOfReplies.get(i).getInquiryId().equals(updatedReply.getInquiryId())) {
                updatedReply.setReplyId(listOfReplies.get(i).getReplyId()); // 기존 ID 유지
                updatedReply.setReplyDate(LocalDateTime.now()); // 수정일시 갱신
                listOfReplies.set(i, updatedReply);
                return;
            }
        }
    }
}
