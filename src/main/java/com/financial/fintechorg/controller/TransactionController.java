package com.financial.fintechorg.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.financial.fintechorg.dto.TransactionDTO;
import com.financial.fintechorg.service.impl.TransactionServiceImpl;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
@Slf4j
@Api("Handles transactions across registered users")
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void doTransaction(@Valid @RequestBody TransactionDTO request) {
        log.debug("start POST transaction");

        transactionService.createTransaction(request);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionDTO>> findAllByUser(@PathVariable UUID userId) {
        log.debug("start GET transactions from id {}", userId);

        return ResponseEntity.ok(transactionService.findByUserId(userId));
    }
}
