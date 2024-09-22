package br.com.ibmec.transacao_api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {

    @NotBlank(message = "O número do cartão é obrigatório.")
    private String cardNumber;

    @NotNull(message = "O limite disponível é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O limite disponível deve ser maior que zero.")
    private BigDecimal availableLimit;
}