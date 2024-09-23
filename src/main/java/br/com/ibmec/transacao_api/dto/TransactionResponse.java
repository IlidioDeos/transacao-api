package br.com.ibmec.transacao_api.dto;

import br.com.ibmec.transacao_api.model.TransactionStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private String merchant;
    private LocalDateTime timestamp;
    private TransactionStatus status;
    private String rejectionReason;
    private String message; // Novo campo para mensagens de notificação
}
