package kr.ac.kopo.dgj.web_term_2026.repository;

import kr.ac.kopo.dgj.web_term_2026.domain.Reply;

public interface ReplyRepository {

    Reply getReplyByInquiryId(String inquiryId); // 문의 ID로 답변 조회 (없으면 null)

    void setNewReply(Reply reply); // 새 답변 저장

    void updateReply(Reply reply); // 기존 답변 수정
}
