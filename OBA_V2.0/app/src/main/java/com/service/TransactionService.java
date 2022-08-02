package com.service;

import com.dto.TransactionDTO;
import com.entity.Transaction;
import javassist.NotFoundException;

import java.util.List;

public interface TransactionService {

    Transaction dtoToEntity(TransactionDTO transactionDTO);

    List<TransactionDTO> getTransactionsByUser(Long userId) throws NotFoundException;

    TransactionDTO get(Long id) throws NotFoundException;

    TransactionDTO save(TransactionDTO transactionDTO);

    void delete(Long id) throws NotFoundException;
}
