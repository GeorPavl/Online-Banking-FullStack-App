package com.serverside.controller;

import com.serverside._helpers.GenAccountNumber;
import com.serverside.model.User;
import com.serverside.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create_account")
    public String createAccount(@RequestParam("account_name") String accountName,
                                @RequestParam("account_type") String accountType,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {

        // TODO: 24/7/2022 Check for empty strings
        if (accountName == null || accountName.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Account Name Cannot Be Empty!");
            return "redirect:/app/dashboard";
        } else if (accountType == null || accountType.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Account Type Cannot Be Empty!");
            return "redirect:/app/dashboard";
        }

        // TODO: 24/7/2022 Get logged in user
        User user = (User)session.getAttribute("user");

        // TODO: 24/7/2022 Generate Account Number
        int accountNumber = GenAccountNumber.generateAccountNumber();

        // TODO: 24/7/2022 Create Account
        accountService.createAccount(user.getId(), Integer.toString(accountNumber), accountName, accountType);

        // TODO: 24/7/2022 Set success message
        redirectAttributes.addFlashAttribute("success", "Account Created Successfully!");
        return "redirect:/app/dashboard";
    }
}
