package br.com.ibmec.transacao_api.dto;

import br.com.ibmec.transacao_api.model.Account;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {
    private Long id;
    private String cardNumber;
    private BigDecimal availableLimit;
    private Boolean active;

    public static AccountResponse fromEntity(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setCardNumber(account.getCardNumber());
        response.setAvailableLimit(account.getAvailableLimit());
        response.setActive(account.getActive());
        return response;
    }
}
