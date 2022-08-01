package com.controller;

import com.dto.UserDTO;
import com.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Locale;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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

        // Check for errors
        if (!userDTO.getPassword().equals(confirmPassword)) {
            errorMessage = messageSource.getMessage("errors.registration.passwordMisMatch", null, locale);
            model.addAttribute("error", errorMessage);
            return "error";
        }
        // Register user
        UserDTO registeredUser = userService.register(userDTO);
        // Check if registration was successful
        if (registeredUser == null) {
            errorMessage = messageSource.getMessage("errors.registration.general", null, locale);
            model.addAttribute("error", errorMessage);
            return "error";
        }
        String successMessage = messageSource.getMessage("success.registration", null, locale);
        model.addAttribute("success", successMessage);
        return "login";
    }


    @GetMapping("/user-panel")
    public String getUserPanelPage(Model model) {
        title = messageSource.getMessage("titles.userPanel", null, locale);
        model.addAttribute("pageTitle", title);
        return "user/user-index";
    }
}
