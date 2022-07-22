package com.serverside.controller;

import com.serverside.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/app")
public class AppController {

    @GetMapping("/dashboard")
    public String getDashboard(HttpSession session, Model model) {
        // Get the details of logged in User
        User user = (User)session.getAttribute("user");
        model.addAttribute("user", user);

        // Get the accounts of logged in User

        // Get Balance

        // Set Objects

        return "dashboard";
    }
}
