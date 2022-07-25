package com.demobank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("PageTitle", "Register");
        return "register";
    }
}
