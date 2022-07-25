package com.serverside.controller;

import com.serverside.model.Account;
import com.serverside.model.PaymentHistory;
import com.serverside.model.TransactionHistory;
import com.serverside.model.User;
import com.serverside.service.AccountService;
import com.serverside.service.PaymentHistoryService;
import com.serverside.service.TransactHistoryService;
import com.serverside.service.TransactService;
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
    @Autowired
    private PaymentHistoryService paymentHistoryService;
    @Autowired
    private TransactService transactService;
    @Autowired
    private TransactHistoryService transactHistoryService;

    @GetMapping("/dashboard")
    public String getDashboard(HttpSession session, Model model) {
        // Get the details of logged in User
        User user = (User)session.getAttribute("user");

        // TODO: 24/7/2022 Fix Accordion Collapse 
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

    @GetMapping("/payment_history")
    public String getPaymentHistory(HttpSession session, Model model){
        // Get Logged In User:\
        User user = (User) session.getAttribute("user");

        // Get Payment History / Records:
        List<PaymentHistory> userPaymentHistory = paymentHistoryService.getPaymentRecordsById(user.getId());

        model.addAttribute("payment_history", userPaymentHistory);
        model.addAttribute("user", user);
        return "payment_history";
    }

    @GetMapping("/transact_history")
    public String getTransactHistory(HttpSession session, Model model){
        // Get Logged In User:\
        User user = (User) session.getAttribute("user");

        // Get Payment History / Records:
        List<TransactionHistory> userTransactHistory = transactHistoryService.getTransactionRecordsById(user.getId());

        model.addAttribute("transact_history", userTransactHistory);
        model.addAttribute("user", user);

        return "transact_history";
    }
}
