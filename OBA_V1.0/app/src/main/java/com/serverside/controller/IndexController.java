package com.serverside.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("PageTitle", "Home");
        model.addAttribute("theDate", new java.util.Date());
        return "index";
    }

    // TODO: 19/7/2022 Delete sample text and test controller! 
    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        String testString = "In Register Page Controller";
        model.addAttribute("PageTitle", "Register");
        model.addAttribute("SampleText", testString);
        return "register";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        String testString = "In Login Page Controller";
        model.addAttribute("PageTitle", "Login");
        model.addAttribute("SampleText", testString);
        return "login";
    }

    @GetMapping("/hello")
    public String sayHello(Model model) {
        model.addAttribute("theDate", new java.util.Date());
        return "helloworld";
    }
}
