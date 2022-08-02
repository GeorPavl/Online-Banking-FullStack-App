package com.service;

import com.dto.TransactionDTO;
import com.entity.Account;
import com.entity.Transaction;
import com.repository.TransactionRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PaymentService paymentService;

    @Override
    public Transaction dtoToEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(transactionDTO, transaction);
        if (transactionDTO.getAccountId() != null) {
            Account account = new Account();
            account.setId(transactionDTO.getAccountId());
            transaction.setAccount(account);
        }
        if (transactionDTO.getPaymentDTO() != null) {
            transaction.addPayment(paymentService.dtoToEntity(transactionDTO.getPaymentDTO()));
        }
        return transaction;
    }

    @Override
    public TransactionDTO get(Long id) throws NotFoundException {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);

        if (optionalTransaction.isEmpty()) {
            throw new NotFoundException("Did not found transaction with id: " + id);
        }
        return new TransactionDTO(optionalTransaction.get());
    }

    @Override
    public TransactionDTO save(TransactionDTO transactionDTO) {
        return new TransactionDTO(transactionRepository.save(dtoToEntity(transactionDTO)));
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if (get(id) != null) {
            transactionRepository.deleteById(id);
        }
    }
}
