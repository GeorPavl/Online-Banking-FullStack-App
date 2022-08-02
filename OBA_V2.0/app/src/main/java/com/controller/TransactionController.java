package com.controller;

import com.dto.TransactionDTO;
import com.service.AccountService;
import com.service.TransactionService;
import com.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageSource messageSource;

    private static String errorMessage;
    private static String successMessage;
    private static final Locale locale = LocaleContextHolder.getLocale();

    @GetMapping("/transact-history")
    public String getTransactionsList(Model model, RedirectAttributes redirectAttributes) throws NotFoundException {
        try {
            List<TransactionDTO> transactionDTOS = transactionService.getTransactionsByUser();
            model.addAttribute("transactHistory", transactionDTOS);
            model.addAttribute("user", userService.getLoggedInUser());
            return "transact_history";
        } catch (Exception e) {
            errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }
    }

    @PostMapping("/deposit")
    public String deposit(@ModelAttribute TransactionDTO transactionDTO,
                          RedirectAttributes redirectAttributes) throws NotFoundException {
        try {
            transactionService.deposit(transactionDTO);
            successMessage = messageSource.getMessage("success.deposit", null, locale);
            redirectAttributes.addFlashAttribute("success", successMessage);
            return "redirect:/user/user-panel";
        } catch (Exception e) {
            errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }
    }

    @PostMapping("/withdraw")
    public String withdraw(@ModelAttribute TransactionDTO transactionDTO,
                           RedirectAttributes redirectAttributes) throws NotFoundException {
        try {
            transactionService.withdraw(transactionDTO);
            successMessage = messageSource.getMessage("success.withdrawal", null, locale);
            redirectAttributes.addFlashAttribute("success", successMessage);
            return "redirect:/user/user-panel";
        } catch (Exception e) {
            errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }
    }

    @PostMapping("/transfer")
    public String transfer(@ModelAttribute TransactionDTO transactionDTO,
                           RedirectAttributes redirectAttributes) {
        try {
            transactionService.transfer(transactionDTO);
            successMessage = messageSource.getMessage("success.transfer", null, locale);
            redirectAttributes.addFlashAttribute("success", successMessage);
            return "redirect:/user/user-panel";
        } catch (Exception e) {
            errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }
    }

    @PostMapping("/payment")
    public String payment(@ModelAttribute TransactionDTO transactionDTO,
                          RedirectAttributes redirectAttributes) throws NotFoundException {
        try {
            transactionService.payment(transactionDTO);
            successMessage = messageSource.getMessage("success.payment", null, locale);
            redirectAttributes.addFlashAttribute("success", successMessage);
            return "redirect:/user/user-panel";
        } catch (Exception e) {
            errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/user-panel";
        }
    }
}
