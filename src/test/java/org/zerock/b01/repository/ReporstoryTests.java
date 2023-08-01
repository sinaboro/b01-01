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

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReporstoryTests {

    @Autowired
    private  BoardReposotory boardReposotory;

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content...." + i)
                    .writer("user" + (i%10)).build();

            Board result = boardReposotory.save(board);
            log.info("bno : " + result.getBno());
        });
    }

    @Test
    public void testRead(){

        Optional<Board> id = boardReposotory.findById(3L);
        Board board = id.orElseThrow();
        log.info(board);
    }
    //title...100

    @Test
    public void testDelete(){

        Optional<Board> id = boardReposotory.findById(3L);
        Board board = id.orElseThrow();
        boardReposotory.delete(board);
    }


    @Test
    public void testPaging(){

        PageRequest request = PageRequest.of(2, 10, Sort.by("bno").descending());
        Page<Board> page = boardReposotory.findAll(request);
        log.info(page.getTotalPages());
        log.info(page.getSize());
        log.info(page.getTotalElements());
        log.info(page.getNumber());

        page.getContent().forEach(board->log.info(board));

    }

    @Test
    public void testWriter(){
        boardReposotory.findBoardByWriter("user5").forEach(
                board->log.info("board : " + board)
        );
    }
    @Test
    public void testWriterAndContent(){
        boardReposotory.findByWriterAndContent("user5","content....75")
                .forEach(board->log.info(board));
    }

    @Test
    public void testBetween(){
        boardReposotory.findByBnoBetween(10L,15L)
                .forEach(board->log.info(board));
    }

    @Test
    public void testLike(){
        boardReposotory.findByWriterLike("%5%")
                .forEach(board->log.info(board));
    }
    @Test
    public void testContaining(){
        boardReposotory.findByWriterContaining("4")
                .forEach(board->log.info(board));
    }

    @Test
    public  void testfindByBnoLessThanOrderByContentDesc(){
        boardReposotory.findByBnoLessThanOrderByContentDesc(100L)
                .forEach(board -> log.info(board));
    }

    @Test
    public void testQuery1(){
        boardReposotory.findByWriterTitleDetail2("2", "2").
                forEach(board -> log.info(Arrays.toString(board)));
    }

    @Test
    public void testKeywordPag(){
        Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").descending());

        Page<Board> page = boardReposotory.findKeyword("2", pageable);
        log.info("page : " + page.getTotalPages());
        log.info("page : " + page.getTotalElements());
        log.info("page : " + page.getSize());
        log.info("page : " + page.getNumber());
        page.getContent().forEach(list -> log.info(list));
    }

    @Test
    public void testOne(){
        Board board = boardReposotory.searchBno(100L);
        log.info("=====> " + board);
    }

    @Test
    public void testByTitle(){
        boardReposotory.findByTitle2("2").forEach(board->log.info("Title : " + board));
    }

    @Test
    public void testSearch1(){
        Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").descending());
        Page<Board> search1 = boardReposotory.search1(pageable);

        log.info("getTotalPages :" + search1.getTotalPages());
        log.info("getTotalElements : " + search1.getTotalElements());
        search1.getContent().forEach(list->log.info(list));
    }
}
