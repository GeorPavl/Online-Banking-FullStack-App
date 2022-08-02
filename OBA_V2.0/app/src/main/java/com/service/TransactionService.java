package com.service;

import com.dto.TransactionDTO;
import com.entity.Transaction;
import javassist.NotFoundException;

import java.util.List;

public interface TransactionService {

    Transaction dtoToEntity(TransactionDTO transactionDTO);

    List<TransactionDTO> getTransactionsByUser() throws NotFoundException;

    TransactionDTO get(Long id) throws NotFoundException;

    TransactionDTO save(TransactionDTO transactionDTO);

    void delete(Long id) throws NotFoundException;

    void deposit(TransactionDTO transactionDTO) throws NotFoundException;

    void withdraw(TransactionDTO transactionDTO) throws NotFoundException;

    void transfer(TransactionDTO transactionDTO) throws NotFoundException;

    void payment(TransactionDTO transactionDTO) throws NotFoundException;
}
