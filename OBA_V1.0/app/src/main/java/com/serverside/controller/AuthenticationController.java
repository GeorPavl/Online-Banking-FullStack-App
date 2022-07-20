package com.serverside.controller;

import com.serverside._helpers.HTML;
import com.serverside._helpers.MailMessenger;
import com.serverside._helpers.Token;
import com.serverside.model.User;
import com.serverside.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Random;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("PageTitle", "Register");
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerUser")User user, BindingResult bindingResult,
                           @RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("confirmPassword") String confirmPassword,
                           Model model) throws MessagingException {
        // Check for errors
        if (bindingResult.hasErrors() && confirmPassword.isEmpty()) {

            model.addAttribute("confirm_pass", "The 'Confirm Password' field is required.");
            return "register";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("pass_match", "Passwords don't match.");
            return "register";
        }
        
        // TODO: 20/7/2022 Get token string 
        String token = Token.generateToken();

        // TODO: 20/7/2022 Generate Random Code
        Random random = new Random();
        int bound = 123;
        int code = bound * random.nextInt(bound);

        // TODO: 20/7/2022 Get email html body
        String emailBody = HTML.htmlEmailTemplate(token, Integer.toString(code));

        // TODO: 20/7/2022 Hash Password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // TODO: 20/7/2022 Register User 
        User newUser = new User(firstName, lastName, email, hashedPassword, token, Integer.toString(code));
        userService.registerUser(newUser);

        // TODO: 20/7/2022 Send Mail notification 
        MailMessenger.htmlEmailMessenger("georpavloglou@gmail.com", email, "Account Verification", emailBody);

        // TODO: 20/7/2022 Return to register page
        String successMessage = "Account created successfully. Please check your email and Verify Account!";
        model.addAttribute("success", successMessage);
        return "register";
    }
}
