package kr.ac.kopo.dgj.web_term_2026.ReplyService;

import kr.ac.kopo.dgj.web_term_2026.domain.Reply;
import kr.ac.kopo.dgj.web_term_2026.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Autowired
    public ReplyServiceImpl(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    @Override
    public Reply getReplyByInquiryId(String inquiryId) {
        return replyRepository.getReplyByInquiryId(inquiryId);
    }

    @Override
    public void setNewReply(Reply reply) {
        replyRepository.setNewReply(reply);
    }

    @Override
    public void updateReply(Reply reply) {
        replyRepository.updateReply(reply);
    }
}
