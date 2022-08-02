package com.controller;

import com._config._helpers._enums.TransactionSource;
import com._config._helpers._enums.TransactionStatus;
import com._config._helpers._enums.TransactionType;
import com.dto.AccountDTO;
import com.dto.PaymentDTO;
import com.dto.TransactionDTO;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/transaction")
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

    @GetMapping("/transact-history")
    public String getTransactionsList(Model model, RedirectAttributes redirectAttributes) throws NotFoundException {
        try {
            List<TransactionDTO> transactionDTOS = transactionService.getTransactionsByUser();
            model.addAttribute("transactHistory", transactionDTOS);
            model.addAttribute("user", userService.getLoggedInUser());
            return "transact_history";
        } catch (Exception e) {
            errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }
    }

    @PostMapping("/deposit")
    public String deposit(@ModelAttribute TransactionDTO transactionDTO,
                          RedirectAttributes redirectAttributes) throws NotFoundException {
        try {
            transactionService.deposit(transactionDTO);
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
    public String withdraw(@ModelAttribute TransactionDTO transactionDTO,
                           RedirectAttributes redirectAttributes) throws NotFoundException {
        try {
            transactionService.withdraw(transactionDTO);
            successMessage = messageSource.getMessage("success.withdrawal", null, locale);
            redirectAttributes.addFlashAttribute("success", successMessage);
            return "redirect:/user/user-panel";
        } catch (Exception e) {
            errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam("transfer_from") String transferFrom,
                           @RequestParam("transfer_to") String transferTo,
                           @RequestParam("transfer_amount") String transferAmount,
                           @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        // Check for empty fields
        if (transferFrom.isEmpty() || transferTo.isEmpty() || transferAmount.isEmpty()) {
            errorMessage = messageSource.getMessage("errors.transfer.empty", null, locale);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }

        // Cast parameters
        Long transferFromId = Long.valueOf(transferFrom);
        Long transferToId = Long.valueOf(transferTo);
        Double transferAmountValue = Double.parseDouble(transferAmount);

        // Check for transferring into the same account
        if (transferFromId == transferToId) {
            errorMessage = messageSource.getMessage("errors.transfer.sameAccount", null, locale);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }

        // Check for zero values
        if (transferAmountValue == 0) {
            errorMessage = messageSource.getMessage("errors.transfer.zeroAmount", null, locale);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }

        try {
            AccountDTO transferFromAccountDTO = accountService.get(transferFromId);
            AccountDTO transferToAccountDTO = accountService.get(transferToId);
            transferFromAccountDTO.transfer(transferAmountValue, transferToAccountDTO);

            transactionService.save(new TransactionDTO(transferFromId, transferAmountValue, TransactionType.TRANSFER, TransactionStatus.SUCCESS, "Transfer Transaction Successful", TransactionSource.ONLINE));
            accountService.save(transferFromAccountDTO);
            accountService.save(transferToAccountDTO);
            successMessage = messageSource.getMessage("success.transfer", null, locale);
            redirectAttributes.addFlashAttribute("success", successMessage);
            return "redirect:/user/user-panel";
        } catch (Exception e) {
            errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }
    }

    @PostMapping("/payment")
    public String payment(@RequestParam("beneficiary")String beneficiary,
                          @RequestParam("account_number")String beneficiaryAccountNumber,
                          @RequestParam("account_id")String accountId,
                          @RequestParam("reference")String reference,
                          @RequestParam("payment_amount")String paymentAmount,
                          @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) throws NotFoundException {
        // Check for empty values
        if (beneficiary.isEmpty() || beneficiaryAccountNumber.isEmpty() || accountId.isEmpty() || reference.isEmpty() || paymentAmount.isEmpty()) {
            errorMessage = messageSource.getMessage("errors.payment.empty", null, locale);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }

        // Cast Parameters
        Long accountIdLong = Long.valueOf(accountId);
        Double paymentAmountDouble = Double.valueOf(paymentAmount);

        // Check for 0 values
        if (paymentAmountDouble == 0) {
            errorMessage = messageSource.getMessage("errors.payment.zeroAmount", null, locale);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/user-panel";
        }

        try {
            AccountDTO paymentFrom = accountService.get(accountIdLong);
            if (paymentFrom.getBalance() < paymentAmountDouble) {
                errorMessage = messageSource.getMessage("errors.payment.insufficientFunds", null, locale);
                redirectAttributes.addFlashAttribute("error", errorMessage);
                return "redirect:/user/user-panel";
            }
            paymentFrom.payment(paymentAmountDouble);
            PaymentDTO paymentDTO = new PaymentDTO(beneficiary, beneficiaryAccountNumber, reference);
            accountService.save(paymentFrom);
            transactionService.save(new TransactionDTO(accountIdLong, paymentAmountDouble, TransactionType.PAYMENT, TransactionStatus.SUCCESS, "Payment Transaction Successful", TransactionSource.ONLINE, paymentDTO));
            successMessage = messageSource.getMessage("success.payment", null, locale);
            redirectAttributes.addFlashAttribute("success", successMessage);
            return "redirect:/user/user-panel";
        } catch (Exception e) {
            errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/user-panel";
        }
    }
}
