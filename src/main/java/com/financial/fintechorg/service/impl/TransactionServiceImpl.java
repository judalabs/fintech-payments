package com.financial.fintechorg.service.impl;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import com.financial.fintechorg.domain.transaction.Transaction;
import com.financial.fintechorg.domain.user.User;
import com.financial.fintechorg.dto.TransactionDTO;
import com.financial.fintechorg.exception.DeniedAuthorizationException;
import com.financial.fintechorg.exception.DuplicatedTransactionException;
import com.financial.fintechorg.repository.TransactionRepository;
import com.financial.fintechorg.service.AuthorizableTransaction;
import com.financial.fintechorg.service.NotifiableTransaction;
import com.financial.fintechorg.service.TransactionService;
import com.financial.fintechorg.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final UserService userServiceImpl;
    private final TransactionRepository transactionRepository;
    private final NotifiableTransaction notifiableTransaction;
    private final AuthorizableTransaction authorizableTransaction;
    private final Clock clock;
    private Long transactionDebounceInMillis;

    @Override
    @Transactional
    public void createTransaction(TransactionDTO transactionDto) {
        final User sender = userServiceImpl.findById(transactionDto.getSender());
        final User receiver = userServiceImpl.findById(transactionDto.getReceiver());
        if(transactionRepository.existsBySenderAndReceiverAndTimestampAfter(sender, receiver, getDebounce()))
            throw new DuplicatedTransactionException(sender, receiver);

        if(!authorizableTransaction.authorize(sender, transactionDto.getAmount()))
            throw new DeniedAuthorizationException(String.format("%s (%s)", sender.getUuid(), transactionDto.getAmount()));

        sender.sendTo(receiver, transactionDto.getAmount());
        userServiceImpl.saveAll(List.of(sender, receiver));

        final Transaction transaction = transactionRepository.save(Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(transactionDto.getAmount())
                .timestamp(LocalDateTime.now(clock))
                .build());

        notifyIfPossible(sender, transaction, "transaction successful");
        notifyIfPossible(receiver, transaction, "new payment received");
        log.debug("transaction {} finished", transaction.getId());
    }

    @Override
    public List<TransactionDTO> findByUserId(UUID id) {
        return transactionRepository.findByUserId(id);
    }
    private void notifyIfPossible(User user, Transaction transaction, String message) {
        try {
            notifiableTransaction.sendNotification(user, message);
        } catch (ResourceAccessException e) {
            log.error("coudn't notify transaction to user. inject your custom handler here", transaction.getId());
        }
    }

    private LocalDateTime getDebounce() {
        return ChronoUnit.MILLIS.addTo(LocalDateTime.now(clock), -transactionDebounceInMillis);
    }

    @Value("${app.transaction.debounce}")
    public void setTransactionDebounceInMillis(Long transactionDebounceInMillis) {
        this.transactionDebounceInMillis = transactionDebounceInMillis;
    }
}
