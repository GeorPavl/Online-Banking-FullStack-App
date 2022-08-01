package com.controller;

import com._config._helpers.GenerateAccountNumber;
import com._config._helpers._enums.AccountType;
import com.dto.AccountDTO;
import com.dto.UserDTO;
import com.service.AccountService;
import com.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @PostMapping("/create_account")
    public String createAccount(@RequestParam("account_name") String accountName,
                                @RequestParam("account_type") String accountType,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) throws NotFoundException {

        // TODO: 24/7/2022 Check for empty strings
        if (accountName == null || accountName.isEmpty()) {
            // TODO: 25/7/2022 Να αντικαταστήσω τα μηνύματα με μεταβλητές (ίσως static final στην αρχή της κλάσης ή
            //  σε κάποιο άλλο αρχείο με μηνύματα.
            redirectAttributes.addFlashAttribute("error", "Account Name Cannot Be Empty!");
            return "redirect:/user/user-index";
        } else if (accountType == null || accountType.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Account Type Cannot Be Empty!");
            return "redirect:/user/user-index";
        }

        // TODO: 24/7/2022 Get logged in user
        UserDTO userDTO = userService.getByUsername(userDetails.getUsername());

        // TODO: 24/7/2022 Generate Account Number
        Integer accountNumber = GenerateAccountNumber.generateAccountNumber();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(userDTO.getId());
        accountDTO.setName(accountName);
        accountDTO.setType(AccountType.valueOf(accountType));
        accountDTO.setNumber(String.valueOf(accountNumber));
        // TODO: 24/7/2022 Create Account
        accountService.save(accountDTO);

        // TODO: 24/7/2022 Set success message
        redirectAttributes.addFlashAttribute("success", "Account Created Successfully!");
        return "redirect:/user/user-index";
    }
}
