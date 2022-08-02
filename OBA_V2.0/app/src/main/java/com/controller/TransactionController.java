package com.controller;

import com._config._helpers._enums.TransactionSource;
import com._config._helpers._enums.TransactionStatus;
import com._config._helpers._enums.TransactionType;
import com.dto.AccountDTO;
import com.dto.TransactionDTO;
import com.service.AccountService;
import com.service.TransactionService;
import com.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private static String successMessage;
    private static final Locale locale = LocaleContextHolder.getLocale();

    @PostMapping("/deposit")
    public String deposit(@RequestParam("account_id") String accountId,
                          @RequestParam("deposit_amount") String depositAmount,
                          RedirectAttributes redirectAttributes) throws NotFoundException {
        // Check for empty strings
        if (depositAmount.isEmpty() || accountId.isEmpty()) {
            errorMessage = messageSource.getMessage("errors.deposit.empty", null, locale);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }

        // Cast params
        Long accountIdLong = Long.valueOf(accountId);
        Double depositAmountValue = Double.parseDouble(depositAmount);

        // Check if amount value is greater than zero
        if (depositAmountValue == 0) {
            errorMessage = messageSource.getMessage("errors.deposit.zeroAmount", null, locale);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }

        // Deposit to account
        try {
            // Get user's account to deposit
            AccountDTO accountDTO = accountService.get(accountIdLong);
            // Deposit money
            accountDTO.deposit(depositAmountValue);
            // Save transaction and new balance
            transactionService.save(new TransactionDTO(accountIdLong, depositAmountValue, TransactionType.DEPOSIT, TransactionStatus.SUCCESS, "Deposit Transaction Successful", TransactionSource.ONLINE));
            accountService.save(accountService.get(accountIdLong));
            // Set attribute
            successMessage = messageSource.getMessage("success.deposit", null, locale);
            redirectAttributes.addFlashAttribute("success", successMessage);
            return "redirect:/user/user-panel";
        } catch (Exception e) {
            errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam("withdrawal_amount")String withdrawalAmount,
                           @RequestParam("account_id")String accountID,
                           RedirectAttributes redirectAttributes) throws NotFoundException {
        // Check for empty values
        if(withdrawalAmount.isEmpty() || accountID.isEmpty()){
            errorMessage = messageSource.getMessage("errors.withdraw.empty", null, locale);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }

        // Cast parameters
        Double withdrawalAmountDouble = Double.parseDouble(withdrawalAmount);
        Long accountIdLong = Long.valueOf(accountID);

        try {
            AccountDTO accountDTO = accountService.get(accountIdLong);
            // Check for zero values
            if (withdrawalAmountDouble == 0){
                errorMessage = messageSource.getMessage("errors.withdraw.zeroAmount", null, locale);
                redirectAttributes.addFlashAttribute("error", errorMessage);
                return "redirect:/user/user-panel";
            } else if (accountDTO.getBalance() < withdrawalAmountDouble) {
                errorMessage = messageSource.getMessage("errors.withdraw.insufficientFunds", null, locale);
                redirectAttributes.addFlashAttribute("error", errorMessage);
                return "redirect:/user/user-panel";
            }
            // Withdraw amount and save transaction / account
            accountDTO.withdraw(withdrawalAmountDouble);
            // Save transaction and new balance
            transactionService.save(new TransactionDTO(accountIdLong, withdrawalAmountDouble, TransactionType.WITHDRAW, TransactionStatus.SUCCESS, "Withdrawal Transaction Successful", TransactionSource.ONLINE));
            accountService.save(accountService.get(accountIdLong));
            // Set attribute
            successMessage = messageSource.getMessage("success.withdrawal", null, locale);
            redirectAttributes.addFlashAttribute("success", successMessage);
            return "redirect:/user/user-panel";
        } catch (Exception e) {
            errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }
    }
}
