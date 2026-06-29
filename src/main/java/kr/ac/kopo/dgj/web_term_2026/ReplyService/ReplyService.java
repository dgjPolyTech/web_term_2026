package kr.ac.kopo.dgj.web_term_2026.ReplyService;

import kr.ac.kopo.dgj.web_term_2026.domain.Reply;

public interface ReplyService {

    Reply getReplyByInquiryId(String inquiryId); // 문의 ID로 답변 조회

    void setNewReply(Reply reply); // 새 답변 등록

    void updateReply(Reply reply); // 기존 답변 수정
}
