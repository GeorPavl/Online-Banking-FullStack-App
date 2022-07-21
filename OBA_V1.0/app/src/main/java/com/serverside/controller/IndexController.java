package com.serverside.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("PageTitle", "Home");
        return "index";
    }

    @GetMapping("/error")
    public String getErrorPage(Model model) {
        model.addAttribute("PageTitle", "Error");
        return "error";
    }
}
