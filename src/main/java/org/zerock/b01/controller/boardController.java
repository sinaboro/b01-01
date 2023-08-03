package org.zerock.b01.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.service.BoardService;

import javax.validation.Valid;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class boardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info("responseDTO=======>"+ responseDTO);
        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/register")
    public void registerGET(){

    }
    @PostMapping("/register")
    public String registerPOST(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes rttr){

        log.info("board POST register....");
        log.info("bindingResult.hasErrors()) ===========> " + bindingResult.hasErrors());

        if(bindingResult.hasErrors()){
            log.info("has error........");
            rttr.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/board/register";
        }

        log.info("boardDTO : " + boardDTO);
        Long bno = boardService.register(boardDTO);
        rttr.addFlashAttribute("result",bno);

        return "redirect:/board/list";
    }


    @GetMapping({"/read" ,"/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model){
        log.info("bno==========> " + bno);
        BoardDTO boardDTO = boardService.readOne(bno);

        model.addAttribute("dto", boardDTO);
//        model.addAttribute("pageRequestDTO", pageRequestDTO);
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO, @Valid BoardDTO boardDTO,
                BindingResult bindingResult, RedirectAttributes rttr){

        if(bindingResult.hasErrors()){
            log.info("errors");
            String link = pageRequestDTO.getLink();
            rttr.addFlashAttribute("errors", bindingResult.getAllErrors());
            rttr.addAttribute("bno", boardDTO.getBno());
            return "redirect:/board/modify?" + link;
        }

        boardService.modify(boardDTO);
        rttr.addFlashAttribute("result", "modified");
        rttr.addAttribute("bno", boardDTO.getBno());
        return "redirect:/board/list";

    }
    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes rttr){
        boardService.remove(bno);
        rttr.addFlashAttribute("result", "removed");
        return "redirect:/board/list";
    }
}
