package com.demobank.controller;

import com.demobank.model.User;
import com.demobank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("PageTitle", "Login");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model, HttpSession session) {

        // Validate input data
        if (email.isEmpty() || email == null || password.isEmpty() || password == null) {
            model.addAttribute("error", "Username or Password can't be empty!");
            return "login";
        }

        // Check if user exists
        User user = userService.findByEmail(email);

        if (user != null) {
            if (!BCrypt.checkpw(password, user.getPassword())) {
                model.addAttribute("error", "Incorrect username or password!");
                return "login";
            }
        } else {
            model.addAttribute("error", "Something went wrong, please contact support!");
            return "error";
        }

        // Check if user is verified
        if (!user.getVerified()) {
            String msg = "This Account is not yet verified, please check email and verify account!";
            model.addAttribute("error", msg);
            return "login";
        }

        session.setAttribute("user", user);
        session.setAttribute("authenticated", true);

        return "redirect:/app/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("logged_out", "Logged out successfully!");

        return "redirect:/login";
    }
}
