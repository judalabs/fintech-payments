package com.financial.fintechorg.dto;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.DecimalMin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {


    private UUID sender;

    private UUID receiver;

    @DecimalMin(value = "0.01", message = "valor minimo = 0.01")
    private BigDecimal amount;
}
