package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class AuthenticationController {

    @Autowired
    private MessageSource messageSource;

    private static String title;
    private static final Locale locale = LocaleContextHolder.getLocale();

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        title = messageSource.getMessage("titles.login", null, locale);
        model.addAttribute("pageTitle", title);
        return "login";
    }

    @GetMapping("/logout")
    public String getLogoutPage(Model model) {
        title = messageSource.getMessage("titles.login", null, locale);
        model.addAttribute("pageTitle", title);
        return "login";
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage(Model model) {
        title = messageSource.getMessage("titles.accessDenied", null, locale);
        model.addAttribute("pageTitle", title);
        return "access-denied";
    }
}
