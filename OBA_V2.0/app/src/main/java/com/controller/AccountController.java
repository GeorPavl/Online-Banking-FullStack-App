package com.controller;

import com._config._helpers.GenerateAccountNumber;
import com._config._helpers._enums.AccountType;
import com.dto.AccountDTO;
import com.dto.UserDTO;
import com.service.AccountService;
import com.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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
    public String createAccount(@RequestParam("name") String accountName,
                                @RequestParam("type") String accountType,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) throws NotFoundException {

        // Check for empty strings
        if (accountName == null || accountName.isEmpty()) {
            errorMessage = messageSource.getMessage("errors.account.emptyName", null, locale);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        } else if (accountType == null || accountType.isEmpty()) {
            errorMessage = messageSource.getMessage("errors.account.emptyName", null, locale);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }

        // Get logged in user
        UserDTO userDTO = userService.getByUsername(userDetails.getUsername());

        // Generate Account Number
        Integer accountNumber = GenerateAccountNumber.generateAccountNumber();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(userDTO.getId());
        accountDTO.setName(accountName);
        accountDTO.setType(AccountType.valueOf(accountType));
        accountDTO.setNumber(String.valueOf(accountNumber));
        // Create Account
        accountService.save(accountDTO);

        // Set success message
        successMessage = messageSource.getMessage("success.account.save", null, locale);
        redirectAttributes.addFlashAttribute("success", successMessage);
        return "redirect:/user/user-panel";
    }
}
