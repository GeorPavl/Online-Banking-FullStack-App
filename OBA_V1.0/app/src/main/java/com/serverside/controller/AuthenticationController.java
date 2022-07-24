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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Random;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    /** Controllers for Registration Process */

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

            model.addAttribute("confirmPass", "The 'Confirm Password' field is required.");
            return "register";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("passwordMisMatch", "Passwords don't match.");
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
        userService.registerUser(firstName, lastName, email, hashedPassword, token, code);

        // TODO: 20/7/2022 Send Mail notification 
        MailMessenger.htmlEmailMessenger("georpavloglou@gmail.com", email, "Account Verification", emailBody);

        // TODO: 20/7/2022 Return to register page
        String successMessage = "Account created successfully. Please check your email and Verify Account!";
        model.addAttribute("success", successMessage);
        return "register";
    }

    @GetMapping("/verify")
    public String getVerifyPage(@RequestParam("token") String token, @RequestParam("code") String code, Model model) {
        model.addAttribute("PageTitle", "Verify");

        String dbToken = userService.checkToken(token);
        if (dbToken == null) {
            model.addAttribute("error", "This session has expired.");
            return "error";
        }

        userService.verifyAccount(token, code);

        model.addAttribute("success", "Account Verified Successfully, please proceed to Log In!");
        return "login";
    }

    /** Controllers for Login/Authentication Process */

    // TODO: 19/7/2022 Fix css library "registration.css". Same CSS styles but different file name.
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("PageTitle", "Login");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        @RequestParam("_token") String token,
                        Model model, HttpSession session) {

        // TODO: 21/7/2022 Αντί να έχω τις μεθόδους checkEmail, checkPassword κτλ μπορώ να "φέρω" το POJO του User
        //  και να πάρω τις πληροφορίες που θέλω από τα fields του.
        
        // VALIDATE INPUT FIELDS / FORM DATA
        if (email.isEmpty() || email == null || password.isEmpty() || password == null) {
            model.addAttribute("error", "Username or Password can't be empty!");
            return "login";
        }
        // CHECK IF EMAIL EXISTS
        String emailInDatabase = userService.checkEmail(email);
        // Check if email exists
        if (emailInDatabase != null && !emailInDatabase.isEmpty()) {
            // Get password from database
            String passwordInDatabase = userService.checkPassword(email);
            // Validate password
            if (!BCrypt.checkpw(password, passwordInDatabase)) {
                model.addAttribute("error", "Incorrect username or password!");
                return "login";
            }
        } else {
            model.addAttribute("error", "Something went wrong, please contact support!");
            return "error";
        }

        // TODO: 21/7/2022 CHECK IF USER ACCOUNT IS VERIFIED
        int verified = userService.isVerified(emailInDatabase);
        if (verified != 1) {
            // TODO: 21/7/2022 Να αλλάξω όλα τα μηνύματα όπως το παρακάτω (με τη χρήση μεταβλητής)
            String msg = "This Account is not yet verified, please check email and verify account!";
            model.addAttribute("error", msg);
            return "login";
        }

        // TODO: 21/7/2022  PROCEED TO LOG USER IN
        User user = userService.getUserDetails(emailInDatabase);
        session.setAttribute("user", user);
        session.setAttribute("token", token);
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
