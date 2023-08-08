package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.BoardImage;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardReposotoryTests {

    @Autowired
    private  BoardReposotory boardReposotory;
    @Test
    public void testInsertWithImages(){
        Board board = Board.builder()
                .title("Image Test")
                .content("첨부파일테스트")
                .writer("tester")
                .build();

        for (int i=0; i<3; i++){
            board.addImage(UUID.randomUUID().toString(), "file"+i + ".jpg");;
        }
        boardReposotory.save(board);
    }

    @Test
    public void testReadWithImages(){
        Optional<Board> board = boardReposotory.findById(1L);
        Board board1 = board.orElseThrow();
        log.info(board1);
        log.info("--------------------------------");
        log.info(board1.getImageSet());
    }

    @Test
    public void testReadWithImage(){
        Optional<Board> result = boardReposotory.findByIdWithImages(4L);

        Board board = result.orElseThrow();
        log.info(board);
        log.info("---------------------------------");
        for(BoardImage boardImage : board.getImageSet()){
            log.info(boardImage);
        }
    }

    @Test
    @Transactional
    @Commit
    public void testModifyImages(){
        Optional<Board> result = boardReposotory.findByIdWithImages(4L);
        Board board = result.orElseThrow();
        board.clearImage();

        for(int i=0; i<2; i++){
            board.addImage(UUID.randomUUID().toString(), "updatefile"+i+".jpg");
        }

        boardReposotory.save(board);
    }
}