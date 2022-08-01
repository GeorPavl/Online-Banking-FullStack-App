package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class IndexController {

    @Autowired
    private MessageSource messageSource;

    private static String title;
    private static final Locale locale = LocaleContextHolder.getLocale();

    @GetMapping("/")
    public String getHomePage(Model model) {
        title = messageSource.getMessage("titles.home", null, locale);
        model.addAttribute("pageTitle", title);
        return "index";
    }

    @GetMapping("/error")
    public String getErrorPage(Model model) {
        title = messageSource.getMessage("titles.error", null, locale);
        model.addAttribute("pageTitle", title);
        return "error";
    }

    @GetMapping("/admin-panel")
    public String getAdminPanelPage(Model model) {
        title = messageSource.getMessage("titles.adminPanel", null, locale);
        model.addAttribute("pageTitle", title);
        return "admin-index";
    }

    @GetMapping("/user-panel")
    public String getUserPanelPage(Model model) {
        title = messageSource.getMessage("titles.userPanel", null, locale);
        model.addAttribute("pageTitle", title);
        return "user/user-index";
    }
}
