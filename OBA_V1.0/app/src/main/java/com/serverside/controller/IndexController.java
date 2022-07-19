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

    // TODO: 19/7/2022 Fix css library "registration.css". Same CSS styles but different file name.
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("PageTitle", "Login");
        return "login";
    }

    @GetMapping("/error")
    public String getErrorPage(Model model) {
        model.addAttribute("PageTitle", "Error");
        return "error";
    }

    @GetMapping("/verify")
    public String getVerifyPage(Model model) {
        model.addAttribute("PageTitle", "Verify");
        return "login";
    }
}
