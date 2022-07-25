package com.demobank.controller;

import com.demobank.model.User;
import com.demobank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
public class AuthenticationController {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("PageTitle", "Register");
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerUser") User user,
                           @RequestParam("confirmPassword") String confirmPassword,
                           BindingResult bindingResult, Model model) throws MessagingException {

        // Check for errors
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordMisMatch", "Passwords don't match.");
            return "register";
        }

        // Register new user
        User registeredUser = userService.registerUser(user);
        // Check if registration was successful
        if (!registeredUser.equals(null)) {
            model.addAttribute("success", messageSource.getMessage("success.user.register", null, LocaleContextHolder.getLocale()));
        }
        // Return to register page
        return "register";
    }

    @GetMapping("/verify")
    public String verify(@RequestParam("token") String token,
                         @RequestParam("code") String code,
                         Model model) {
        // Check if session is expired
        if (userService.checkToken(token) == null) {
            model.addAttribute("error", messageSource.getMessage("errors.user.sessionExpired", null, LocaleContextHolder.getLocale()));
            return "error";
        }
        // Verify user
        userService.verifyUser(token, code);
        model.addAttribute("success", messageSource.getMessage("success.user.verify", null, LocaleContextHolder.getLocale()));

        return "login";
    }
}
