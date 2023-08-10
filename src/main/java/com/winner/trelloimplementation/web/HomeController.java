package com.winner.trelloimplementation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/web")
    public String home() {
        return "index";
    }
}
