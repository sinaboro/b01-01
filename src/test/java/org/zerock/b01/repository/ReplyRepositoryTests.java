package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsert(){
        Long bno = 1L;

        Board board = Board.builder().bno(bno).build();

        Reply reply = Reply.builder()
                .board(board)
                .replyText("댓글 테스트....")
                .replyer("user00")
                .build();

        replyRepository.save(reply);
    }

    @Test
    public void testBoardReplies(){
        Long bno = 1L;

        Pageable pageable =
                PageRequest.of(1, 3, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        result.getContent().forEach(reply-> log.info(reply));
    }

















}