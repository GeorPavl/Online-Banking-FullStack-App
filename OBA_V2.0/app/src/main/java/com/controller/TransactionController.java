package com.controller;

import com._config._helpers._enums.TransactionSource;
import com._config._helpers._enums.TransactionStatus;
import com._config._helpers._enums.TransactionType;
import com.dto.AccountDTO;
import com.dto.TransactionDTO;
import com.dto.UserDTO;
import com.service.AccountService;
import com.service.TransactionService;
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
@RequestMapping("/transact")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageSource messageSource;

    private static String errorMessage;
    private static final Locale locale = LocaleContextHolder.getLocale();

    @PostMapping("/deposit")
    public String deposit(@RequestParam("account_id") String accountId,
                          @RequestParam("deposit_amount") String depositAmount,
                          @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) throws NotFoundException {
        // Check for empty strings
        if (depositAmount.isEmpty() || accountId.isEmpty()) {
            errorMessage = messageSource.getMessage("errors.deposit.empty", null, locale);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }
        // Cast params
        Long accountIdInt = Long.valueOf(accountId);
        Double depositAmountValue = Double.parseDouble(depositAmount);
        // Check if amount value is greater than zero
        if (depositAmountValue == 0) {
            errorMessage = messageSource.getMessage("errors.deposit.zeroAmount", null, locale);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }
        try {
            // Get logged in user
            UserDTO userDTO = userService.getByUsername(userDetails.getUsername());
            // Get user's account to deposit
            AccountDTO accountDTO = accountService.get(accountIdInt);
            // Deposit money
            accountDTO.deposit(depositAmountValue);
            // Save transaction
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAccountId(accountIdInt);
            transactionDTO.setAmount(depositAmountValue);
            transactionDTO.setSource(TransactionSource.ONLINE);
            transactionDTO.setType(TransactionType.DEPOSIT);
            transactionDTO.setStatus(TransactionStatus.SUCCESS);
            transactionDTO.setReasonCode("Deposit Transaction Successful");
            transactionService.save(transactionDTO);
            accountService.save(accountDTO);
            redirectAttributes.addFlashAttribute("success", "Amount Deposited Successfully!");
            return "redirect:/user/user-panel";
        } catch (Exception e) {
            errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }

    }
}
