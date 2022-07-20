package com.serverside.controller;

import com.serverside.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class AuthenticationController {

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
                           Model model) {
        // Check for errors
        if (bindingResult.hasErrors() && confirmPassword.isEmpty()) {

            model.addAttribute("confirm_pass", "The 'Confirm Password' field is required.");
            return "register";
        }

<<<<<<< HEAD
        if (!password.equals(confirm_password)) {
            model.addAttribute("pass_match", "Passwords don't match.");
            return "register";
        }
        // TODO: 20/7/2022 Get token string 

        // TODO: 20/7/2022 Generate Random Code

        // TODO: 20/7/2022 Hash Password 

        // TODO: 20/7/2022 Register User 

        // TODO: 20/7/2022 Send Mail notification 

        // TODO: 20/7/2022 Return to register page


=======
>>>>>>> master
        return "register";
    }
}
