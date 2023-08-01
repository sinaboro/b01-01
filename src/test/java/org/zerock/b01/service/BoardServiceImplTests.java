package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardServiceImplTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample title..")
                .content("Sample Content")
                .writer("user00").build();

        /*BoardDTO b = new BoardDTO();
        b.setTitle("Sample title..");
        b.setContent("Sample Content");
        b.setWriter("user00");*/

        Long bno = boardService.register(boardDTO);
        log.info("bno : " + bno);
    }

    @Test
    public void testReadOne(){
        BoardDTO boardDTO = boardService.readOne(101L);

        log.info("boardDTO : " + boardDTO);
    }

    @Test
    public void tesModify(){
        BoardDTO boardDTO = BoardDTO.builder().
                 bno(101L).title("수정 테스트").
        content("수정 내용 테스트").build();
        boardService.modify(boardDTO);
    }


    @Test
    public void testRemove(){
        boardService.remove(101L);
    }


    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO =
                PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO =
                boardService.list(pageRequestDTO);
        log.info("responseDTO====> " + responseDTO);
    }

}