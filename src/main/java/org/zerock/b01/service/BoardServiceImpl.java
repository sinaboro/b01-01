package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.BoardListReplyCountDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.repository.BoardReposotory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements  BoardService{

    private final ModelMapper modelMapper;   //DTO(BoardDTO) --> Entity(Board) 변환하기위해서
    private final BoardReposotory boardReposotory;  //영속계층에 저장하기위해서..

    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);
        Long bno = boardReposotory.save(board).getBno();
        //--------       DB저장 -------------->저장된 bno값을 반환

        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {

        Optional<Board> result = boardReposotory.findById(bno);
        Board board = result.orElseThrow();

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardReposotory.findById(boardDTO.getBno());

        Board board = result.orElseThrow();
        board.change(boardDTO.getTitle(), boardDTO.getContent());
        boardReposotory.save(board);   //save --> insert, update두 기능 수행
    }

    @Override
    public void remove(Long bno) {
        boardReposotory.deleteById(bno);
    }

               /* .type("tcw")
                .keyword("1")
                .page(1)
                .size(10) */
    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();  //t c w
        String keyword = pageRequestDTO.getKeyword();  //2
        Pageable pageable = pageRequestDTO.getPageable("bno"); //0,10 bno.desc

        Page<Board> result = boardReposotory.searchAll(types,keyword,pageable);
        //확인 검색.......
        log.info("----------------------------------------------------------");
        log.info("aaa getTotalPages: " + result.getTotalPages());
        log.info("aaa getSize: " + result.getSize());
        log.info("aaa getTotalElements: " + result.getTotalElements());
        result.getContent().forEach(board->log.info(board));
        log.info("----------------------------------------------------------");
        //---------------------------

        //board(entity)---> boardDTO로 변환(mapper)
        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board,BoardDTO.class)).
                collect(Collectors.toList());

        PageResponseDTO<BoardDTO> pageResponseDTO =
                new PageResponseDTO<>(pageRequestDTO,
                        dtoList, (int) result.getTotalElements());

        return pageResponseDTO;

        /*return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build(); */

    }

    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();  //t c w
        String keyword = pageRequestDTO.getKeyword();  //2
        Pageable pageable = pageRequestDTO.getPageable("bno"); //0,10 bno.desc

        Page<BoardListReplyCountDTO> result = boardReposotory.searchWithReplyCount(types,keyword,pageable);

       /* PageResponseDTO<BoardListReplyCountDTO> pageResponseDTO =
                new PageResponseDTO<>(pageRequestDTO,
                        result.getContent(), (int) result.getTotalElements());
        return pageResponseDTO;*/

       return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }


}
