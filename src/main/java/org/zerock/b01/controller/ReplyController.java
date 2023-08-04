package org.zerock.b01.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.RelpyDTO;

import javax.validation.Valid;
import java.awt.*;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
public class ReplyController {

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String ,Long>> register(@Valid @RequestBody RelpyDTO relpyDTO,
                                                                    BindingResult bindingResult) throws BindException{

        log.info("replyDTO =>" + relpyDTO);

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }
        Map<String, Long> resultMap = Map.of("rno", 1L);

        return  ResponseEntity.ok(resultMap);
    }
}
