package br.com.ibmec.transacao_api.service.rules;

import br.com.ibmec.transacao_api.dto.TransactionRequest;
import br.com.ibmec.transacao_api.exception.TransactionRejectedException;
import br.com.ibmec.transacao_api.model.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RuleLimitAvailable implements TransactionRule {

    @Override
    public void apply(Account account, TransactionRequest request) {
        BigDecimal availableLimit = account.getAvailableLimit();
        if (request.getAmount().compareTo(availableLimit) > 0) {
            throw new TransactionRejectedException("limite-insuficiente");
        }
    }
}