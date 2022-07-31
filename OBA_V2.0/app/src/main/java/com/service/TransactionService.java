package com.service;

import com.dto.TransactionDTO;
import com.entity.Transaction;
import javassist.NotFoundException;

public interface TransactionService {

    Transaction dtoToEntity(TransactionDTO transactionDTO);

    TransactionDTO get(Long id) throws NotFoundException;

    TransactionDTO save(TransactionDTO transactionDTO);

    void delete(Long id) throws NotFoundException;
}
