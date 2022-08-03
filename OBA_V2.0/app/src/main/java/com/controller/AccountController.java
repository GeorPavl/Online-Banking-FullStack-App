package com.controller;

import com.dto.AccountDTO;
import com.service.AccountService;
import com.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String createAccount(@ModelAttribute AccountDTO accountDTO,
                                RedirectAttributes redirectAttributes) throws NotFoundException {
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

    @PostMapping("/delete")
    public String deleteAccount(@RequestParam("id") Long id,
                                RedirectAttributes redirectAttributes) throws NotFoundException{
        try {
            accountService.delete(id);
            successMessage = messageSource.getMessage("success.account.delete", null,locale);
            redirectAttributes.addFlashAttribute("success", successMessage);
            return "redirect:/user/user-panel";
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/user-panel";
        }
    }
}