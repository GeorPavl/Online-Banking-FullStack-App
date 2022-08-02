package com.controller;

import com.dto.UserDTO;
import com.service.AccountService;
import com.service.UserService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Locale;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageSource messageSource;

    private static String title;
    private static final Locale locale = LocaleContextHolder.getLocale();

    private String errorMessage;

    @GetMapping("/register")
    public String registerUser(Model model) {
        title = messageSource.getMessage("titles.register", null, locale);
        model.addAttribute("pageTitle", title);
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDTO userDTO,
                               @RequestParam("confirmPassword") String confirmPassword,
                               Model model) throws NotFoundException, MessagingException {

        title = messageSource.getMessage("titles.register", null, locale);
        model.addAttribute("pageTitle", title);
        // TODO: 2/8/2022 Να περάσει το confirmPassword στο DTO και ο έλεγχος στο service 
        // Check for errors
        if (!userDTO.getPassword().equals(confirmPassword)) {
            errorMessage = messageSource.getMessage("errors.registration.passwordMisMatch", null, locale);
            model.addAttribute("error", errorMessage);
            return "error";
        }

        try {
            userService.register(userDTO);
            String successMessage = messageSource.getMessage("success.registration", null, locale);
            model.addAttribute("success", successMessage);
            return "login";
        } catch (Exception e) {
            errorMessage = messageSource.getMessage("errors.registration.general", null, locale);
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/user-panel")
    public String getUserPanelPage(@AuthenticationPrincipal UserDetails userDetails, Model model) throws NotFoundException {
        title = messageSource.getMessage("titles.userPanel", null, locale);
        model.addAttribute("pageTitle", title);

        try {
            UserDTO userDTO = userService.getByUsername(userDetails.getUsername());
            // TODO: 24/7/2022 Fix Accordion Collapse
            model.addAttribute("user", userDTO);
        } catch (Exception e) {
            errorMessage = messageSource.getMessage("errors.user.notFound", null, locale);
            model.addAttribute("error", errorMessage);
            return "error";
        }

        return "user/user-index";
    }
}
