package br.com.ibmec.transacao_api.dto;

import br.com.ibmec.transacao_api.model.Transaction;
import br.com.ibmec.transacao_api.model.TransactionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private String merchant;
    private LocalDateTime timestamp;
    private TransactionStatus status;
    private String rejectionReason;

    public static TransactionResponse fromEntity(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setMerchant(transaction.getMerchant());
        response.setTimestamp(transaction.getTimestamp());
        response.setStatus(transaction.getStatus());
        response.setRejectionReason(transaction.getRejectionReason());
        return response;
    }
}
