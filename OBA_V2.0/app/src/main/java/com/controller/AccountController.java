package com.controller;

import com._config._helpers._enums.AccountType;
import com.dto.AccountDTO;
import com.service.AccountService;
import com.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    private static final Locale locale = LocaleContextHolder.getLocale();
    private static String errorMessage;
    private static String successMessage;

    @PostMapping("/create_account")
    public String createAccount(@RequestParam("name") String accountName,
                                @RequestParam("type") String accountType,
                                @ModelAttribute AccountDTO accountDTO,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) throws NotFoundException {
        if (accountDTO.getType() != null) {
            accountDTO.setType(AccountType.valueOf(accountType));
        }
        try {
            accountService.createAccount(accountDTO);
            successMessage = messageSource.getMessage("success.account.save", null, locale);
            redirectAttributes.addFlashAttribute("success", successMessage);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
        }
        return "redirect:/user/user-panel";
    }
}