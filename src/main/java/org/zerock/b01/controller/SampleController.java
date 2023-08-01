package org.zerock.b01.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@Log4j2
public class SampleController {

    @GetMapping("/ex/ex3")
    public void ex3(Model model){
        model.addAttribute("arr", new String[]{"AAA","BBB","CCC"});
    }

    @GetMapping("/ex/ex1")
    public void ex1(Model model){
        List<String> list = Arrays.asList("AAA","BBB","CCC","DDD");
        model.addAttribute("list", list);
        model.addAttribute("msg","Hello <b>Spring</b>");
        model.addAttribute("gender", "man");
    }

    @GetMapping("/hello")
    public void hello(Model model){
        log.info("Hello..............");
        model.addAttribute("msg", "Hello World~~");
        model.addAttribute("name", "김대철");
    }

    @GetMapping("/helloArr")
    public @ResponseBody String[] helloArr(){
        log.info("hello Arr...........");
        return new String[]{"AAAA","BBB","CCC"};
    }
}
