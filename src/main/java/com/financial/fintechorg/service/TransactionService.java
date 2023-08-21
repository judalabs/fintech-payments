package com.financial.fintechorg.service;

import java.util.List;
import java.util.UUID;

import com.financial.fintechorg.dto.TransactionDTO;

public interface TransactionService {

    void createTransaction(TransactionDTO transactionDto);

    List<TransactionDTO> findByUserId(UUID id);
}
