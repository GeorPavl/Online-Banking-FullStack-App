package com.serverside.controller;

import com.serverside.model.Account;
import com.serverside.model.User;
import com.serverside.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/dashboard")
    public String getDashboard(HttpSession session, Model model) {
        // Get the details of logged in User
        User user = (User)session.getAttribute("user");

        // Get the accounts of logged in User
        List<Account> accounts = accountService.getUserAccounts(user.getId());

        // Get Balance
        BigDecimal totalBalance = accountService.getTotalBalance(user.getId());

        // Set Objects
        model.addAttribute("user", user);
        model.addAttribute("userAccounts", accounts);
        model.addAttribute("totalBalance", totalBalance);

        return "dashboard";
    }
}
