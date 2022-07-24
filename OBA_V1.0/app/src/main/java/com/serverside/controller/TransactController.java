package com.serverside.controller;

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
@RequestMapping("/transact")
public class TransactController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/deposit")
    public String deposit(@RequestParam("deposit_amount") String depositAmount,
                          @RequestParam("account_id") String accountId,
                          HttpSession session, RedirectAttributes redirectAttributes) {

        // TODO: 24/7/2022 Check for empty strings
        if (depositAmount.isEmpty() || accountId.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Deposit Amount and Account Cannot Be Empty.");
            return "redirect:/app/dashboard";
        }

        // TODO: 24/7/2022 Get Logged in user 
        User user = (User)session.getAttribute("user");

        // TODO: 24/7/2022 Get Current Account Balance
        int account_id = Integer.parseInt(accountId);
        double currentBalance = accountService.getAccountBalance(user.getId(), account_id);

        // TODO: 24/7/2022 Check if deposit ammount is 0
        double depositAmountValue = Double.parseDouble(depositAmount);
        if (depositAmountValue == 0) {
            redirectAttributes.addFlashAttribute("error", "Deposit Amount Cannot Be 0.");
            return "redirect:/app/dashboard";
        }

        // TODO: 24/7/2022 Update Balance
        double new_balance = currentBalance + depositAmountValue;
        accountService.changeAccountBalanceById(new_balance, account_id);
        redirectAttributes.addFlashAttribute("success", "Amount Deposited Successfully!");

        return "redirect:/app/dashboard";
    }
}
