package com.financial.fintechorg.service.impl;

import static com.financial.fintechorg.helper.UUIDHelper.defaultUuid;
import static com.financial.fintechorg.helper.UUIDHelper.uuid2;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;

import com.financial.fintechorg.domain.user.User;
import com.financial.fintechorg.domain.user.UserType;
import com.financial.fintechorg.dto.TransactionDTO;
import com.financial.fintechorg.exception.DeniedAuthorizationException;
import com.financial.fintechorg.exception.InvalidSenderException;
import com.financial.fintechorg.helper.TestClockConfiguration;
import com.financial.fintechorg.repository.TransactionRepository;
import com.financial.fintechorg.service.AuthorizableTransaction;
import com.financial.fintechorg.service.NotifiableTransaction;
import com.financial.fintechorg.service.UserService;


class TransactionServiceImplTest {

    private TransactionServiceImpl sut;
    private UserService userService;
    private TransactionRepository transactionRepository;
    private NotifiableTransaction notifiableTransaction;
    private AuthorizableTransaction authorizableTransaction;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        transactionRepository = mock(TransactionRepository.class);
        notifiableTransaction = mock(NotifiableTransaction.class);
        authorizableTransaction = mock(AuthorizableTransaction.class);

        sut = new TransactionServiceImpl(userService, transactionRepository, notifiableTransaction,
                authorizableTransaction, new TestClockConfiguration().fixedClock());

        sut.setTransactionDebounceInMillis(1000L);
    }

    @Test
    void shouldThrowExceptionWhenFoundFreshTransactionBetweenPairs() {
        final var transaction = new TransactionDTO(defaultUuid, uuid2, BigDecimal.valueOf(123));
        final var sender = User.builder().userType(UserType.MERCHANT).firstName("ab").build();
        final var receiver = User.builder().userType(UserType.MERCHANT).firstName("cd").build();

        when(userService.findById(defaultUuid)).thenReturn(sender);
        when(userService.findById(uuid2)).thenReturn(receiver);
        when(transactionRepository.existsBySenderAndReceiverAndTimestampAfter(any(), any(), any())).thenReturn(true);

        assertThrows(DuplicatedTransactionException.class, () -> sut.createTransaction(transaction));
    }

    @Test
    void shouldThrowExceptionWhenNotAuthorized() {
        final var transaction = new TransactionDTO(defaultUuid, uuid2, BigDecimal.valueOf(123));
        final var sender = User.builder().userType(UserType.MERCHANT).build();

        when(userService.findById(defaultUuid)).thenReturn(sender);

        assertThrows(DeniedAuthorizationException.class, () -> sut.createTransaction(transaction));
    }

    @Test
    void shouldThrowExceptionWhenSenderIsMerchant() {
        final var transaction = new TransactionDTO(defaultUuid, uuid2, BigDecimal.valueOf(123));
        final var sender = User.builder().uuid(defaultUuid).userType(UserType.MERCHANT).build();

        when(authorizableTransaction.authorize(sender, BigDecimal.valueOf(123))).thenReturn(true);
        when(userService.findById(defaultUuid)).thenReturn(sender);

        assertThrows(InvalidSenderException.class, () -> sut.createTransaction(transaction));
    }

    @Test
    void shouldSaveTransactionSuccessfully() {
        final var transaction = new TransactionDTO(defaultUuid, uuid2, BigDecimal.valueOf(123));
        final var sender = User.builder().uuid(defaultUuid).userType(UserType.COMMON).balance(BigDecimal.valueOf(123)).build();
        final var receiver = User.builder().uuid(uuid2).userType(UserType.COMMON).balance(BigDecimal.valueOf(123)).build();

        when(transactionRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(authorizableTransaction.authorize(sender, BigDecimal.valueOf(123))).thenReturn(true);
        when(userService.findById(defaultUuid)).thenReturn(sender);
        when(userService.findById(uuid2)).thenReturn(receiver);

        Assertions.assertDoesNotThrow(() -> sut.createTransaction(transaction));

        verify(notifiableTransaction).sendNotification(sender, "transaction successful");
        verify(notifiableTransaction).sendNotification(receiver, "new payment received");
    }
}