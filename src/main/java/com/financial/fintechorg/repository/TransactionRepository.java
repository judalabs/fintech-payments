package com.financial.fintechorg.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.financial.fintechorg.domain.transaction.Transaction;
import com.financial.fintechorg.domain.user.User;
import com.financial.fintechorg.dto.TransactionDTO;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT new com.financial.fintechorg.dto.TransactionDTO(T.sender.id, T.receiver.id, T.amount) " +
            " FROM Transaction T " +
            " WHERE T.sender.uuid = :userId " +
            " OR T.receiver.uuid = :userId")
    List<TransactionDTO> findByUserId(@Param("userId") UUID userId);

    boolean existsBySenderAndReceiverAndTimestampAfter(User sender, User receiver, LocalDateTime after);

}
