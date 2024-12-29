package com.bootx.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IndexController {

    @PostMapping("/index")
    public String index(){
        return "index";
    }
}
