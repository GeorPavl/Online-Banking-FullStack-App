package com.serverside.controller;

import com.serverside.model.User;
import com.serverside.service.AccountService;
import com.serverside.service.PaymentService;
import com.serverside.service.TransactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/transact")
public class TransactController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactService transactService;
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/deposit")
    public String deposit(@RequestParam("deposit_amount") String depositAmount,
                          @RequestParam("account_id") String accountId,
                          HttpSession session, RedirectAttributes redirectAttributes) {

        // TODO: 24/7/2022 Check for empty strings
        if (depositAmount.isEmpty() || accountId.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Deposit Amount and Account Cannot Be Empty.");
            return "redirect:/app/dashboard";
        }

        // TODO: 24/7/2022 Get Logged in user 
        User user = (User)session.getAttribute("user");

        // TODO: 24/7/2022 Get Current Account Balance
        int account_id = Integer.parseInt(accountId);
        double currentBalance = accountService.getAccountBalance(user.getId(), account_id);

        // TODO: 24/7/2022 Check if deposit amount is 0
        double depositAmountValue = Double.parseDouble(depositAmount);
        if (depositAmountValue == 0) {
            redirectAttributes.addFlashAttribute("error", "Deposit Amount Cannot Be 0.");
            return "redirect:/app/dashboard";
        }

        // TODO: 24/7/2022 Update Balance
        double new_balance = currentBalance + depositAmountValue;
        accountService.changeAccountBalanceById(new_balance, account_id);
        // Log Successful Transaction:
        transactService.logTransaction(account_id, "Deposit", depositAmountValue, "online", "success", "Deposit Transaction Successful");

        redirectAttributes.addFlashAttribute("success", "Amount Deposited Successfully!");

        return "redirect:/app/dashboard";
    }


    @PostMapping("/transfer")
    public String transfer(@RequestParam("transfer_from") String transfer_from,
                           @RequestParam("transfer_to") String transfer_to,
                           @RequestParam("transfer_amount") String transfer_amount,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {

        // TODO: 25/7/2022 Check for empty fields 
        if (transfer_from.isEmpty() || transfer_to.isEmpty() || transfer_amount.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Transfer accounts and transfer amount can't be empty!");
            return "redirect:/app/dashboard";
        }
        // TODO: 25/7/2022 Convert variables
        int transferFromId = Integer.parseInt(transfer_from);
        int transferToId = Integer.parseInt(transfer_to);
        double transferAmountValue = Double.parseDouble(transfer_amount);

        // TODO: 25/7/2022 Check for transferring into the same account
        if (transferFromId == transferToId) {
            redirectAttributes.addFlashAttribute("error", "Cannot Transfer Into the Same Account. Please Select Appropriate Account to Transfer.");
            return "redirect:/app/dashboard";
        }

        // TODO: 25/7/2022 Check for zero values
        if (transferAmountValue == 0) {
            redirectAttributes.addFlashAttribute("error", "Cannot Transfer an Amount of zero (0)! Please Enter a Value Greater Than Zero.");
            return "redirect:/app/dashboard";
        }
        // TODO: 25/7/2022 Οι διάφορες συναλλαγές πρέπει να γίνονται με μία μέθοδο στο Service
        // TODO: 25/7/2022 Get Logged in user 
        User user = (User)session.getAttribute("user");

        // TODO: 25/7/2022 Get current balances and set new balance
        double currentBalanceFrom = accountService.getAccountBalance(user.getId(), transferFromId);

        // TODO: CHECK IF TRANSFER AMOUNT IS MORE THAN CURRENT BALANCE:
        if(currentBalanceFrom < transferAmountValue){
            // Log Failed Transaction:
            transactService.logTransaction(transferFromId, "Transfer", transferAmountValue, "online", "failed", "Insufficient Funds");
            redirectAttributes.addFlashAttribute("error", "You Have insufficient Funds to perform this Transfer!");
            return "redirect:/app/dashboard";
        }

        double currentBalanceTo = accountService.getAccountBalance(user.getId(), transferToId);
        double newBalanceFrom = currentBalanceFrom - transferAmountValue;
        double newBalanceTo = currentBalanceTo + transferAmountValue;

        accountService.changeAccountBalanceById(newBalanceFrom, transferFromId);
        accountService.changeAccountBalanceById(newBalanceTo, transferToId);

        // Log Successful Transaction:
        transactService.logTransaction(transferFromId, "Transfer", transferAmountValue, "online", "success", "Transfer Transaction Successful");

        redirectAttributes.addFlashAttribute("success", "Amount Transferred Successfully!");

        return "redirect:/app/dashboard";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam("withdrawal_amount")String withdrawalAmount,
                           @RequestParam("account_id")String accountID,
                           HttpSession session,
                           RedirectAttributes redirectAttributes){

        // TODO: CHECK FOR EMPTY VALUES:
        if(withdrawalAmount.isEmpty() || accountID.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Withdrawal Amount and Account Withdrawing From Cannot be Empty ");
            return "redirect:/app/dashboard";
        }
        // TODO: COVERT VARIABLES:
        double withdrawal_amount = Double.parseDouble(withdrawalAmount);
        int account_id = Integer.parseInt(accountID);

        // TODO: CHECK FOR 0 (ZERO) VALUES:
        if (withdrawal_amount == 0){
            redirectAttributes.addFlashAttribute("error", "Withdrawal Amount Cannot be of 0 (Zero) value, please enter a value greater than 0 (Zero)");
            return "redirect:/app/dashboard";
        }
        // TODO: GET LOGGED IN USER:
        User user = (User) session.getAttribute("user");

        // TODO: GET CURRENT BALANCE:
        double currentBalance = accountService.getAccountBalance(user.getId(), account_id);

        // TODO: CHECK IF TRANSFER AMOUNT IS MORE THAN CURRENT BALANCE:
        if(currentBalance < withdrawal_amount){
            // Log Failed Transaction:
            transactService.logTransaction(account_id, "Withdrawal", withdrawal_amount, "online", "failed", "Insufficient Funds");
            redirectAttributes.addFlashAttribute("error", "You Have insufficient Funds to perform this Withdrawal!");
            return "redirect:/app/dashboard";
        }

        // TODO: SET NEW BALANCE:
        double newBalance = currentBalance - withdrawal_amount;

        // TODO: UPDATE ACCOUNT BALANCE:
        accountService.changeAccountBalanceById(newBalance, account_id);

        // Log Successful Transaction:
        transactService.logTransaction(account_id, "Withdrawal", withdrawal_amount, "online", "success", "Withdrawal Transaction Successful");

        redirectAttributes.addFlashAttribute("success", "Withdrawal Successful!");
        return "redirect:/app/dashboard";
    }

    @PostMapping("/payment")
    public String payment(@RequestParam("beneficiary")String beneficiary,
                          @RequestParam("account_number")String account_number,
                          @RequestParam("account_id")String account_id,
                          @RequestParam("reference")String reference,
                          @RequestParam("payment_amount")String payment_amount,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {

        // TODO: CHECK FOR EMPTY VALUES:
        if(beneficiary.isEmpty() || account_number.isEmpty() || account_id.isEmpty() || payment_amount.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Beneficiary, Account Number, Account Paying From and Payment Amount Cannot be Empty!");
            return "redirect:/app/dashboard";
        }

        // TODO: CONVERT VARIABLES:
        int accountID = Integer.parseInt(account_id);
        double paymentAmount = Double.parseDouble(payment_amount);

        // TODO: CHECK FOR 0 (ZERO) VALUES:
        if(paymentAmount == 0){
            redirectAttributes.addFlashAttribute("error", "Payment Amount Cannot be of 0 (Zero) value, please enter a value greater than 0 (Zero) ");
            return "redirect:/app/dashboard";
        }

        // TODO: GET LOGGED IN USER:
        User user = (User) session.getAttribute("user");

        // TODO: GET CURRENT BALANCE:
        double currentBalance = accountService.getAccountBalance(user.getId(), accountID);

        // TODO: CHECK IF PAYMENT AMOUNT IS MORE THAN CURRENT BALANCE:
        if(currentBalance < paymentAmount){
            String reasonCode = "Could not Processed Payment due to insufficient funds!";
            paymentService.makePayment(accountID, beneficiary, account_number, paymentAmount, reference, "failed", reasonCode);
            // Log Failed Transaction:
            transactService.logTransaction(accountID, "Payment", paymentAmount, "online", "failed", "Insufficient Funds");
            redirectAttributes.addFlashAttribute("error", "You Have insufficient Funds to perform this payment");
            return "redirect:/app/dashboard";
        }

        // TODO SET NEW BALANCE FOR ACCOUNT PAYING FROM:
        double newBalance = currentBalance - paymentAmount;

        // TODO: MAKE PAYMENT:
        String reasonCode = "Payment Processed Successfully!";
        paymentService.makePayment(accountID, beneficiary, account_number, paymentAmount, reference, "success", reasonCode);

        // TODO: UPDATE ACCOUNT PAYING FROM:
        accountService.changeAccountBalanceById(newBalance, accountID);

        // Log Successful Transaction:
        transactService.logTransaction(accountID, "Payment", paymentAmount, "online", "success", "Payment Transaction Successful");

        redirectAttributes.addFlashAttribute("success", reasonCode);
        return "redirect:/app/dashboard";
    }
}
