package org.zerock.b01.service;

import com.mysema.commons.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.ReplyDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ReplyServiceImplTests {

    @Autowired
    private ReplyService replyService;

    @Test
    public void testReplyInsert(){
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyText("댓글 테스트중")
                .replyer("조조")
                .bno(12L)
                .build();

        Long rno = replyService.register(replyDTO);
        Assertions.assertNotNull(rno);
    }

    @Test
    public void testRead(){
        ReplyDTO read = replyService.read(11L);
        log.info(read);
    }

    @Test
    public void testModify(){
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyText("이 내용만 수정함4.")
                .rno(11L)
                .build();

        replyService.modify(replyDTO);
    }
}