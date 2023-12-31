package com.financial.fintechorg.dto;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.DecimalMin;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO extends RepresentationModel<TransactionDTO> {

    private UUID sender;

    private UUID receiver;

    @DecimalMin(value = "0.01", message = "valor minimo = 0.01")
    private BigDecimal amount;
}
