package br.com.ibmec.transacao_api.service.rules;

import br.com.ibmec.transacao_api.dto.TransactionRequest;
import br.com.ibmec.transacao_api.exception.TransactionRejectedException;
import br.com.ibmec.transacao_api.model.Account;
import org.springframework.stereotype.Component;

@Component
public class RuleCardActive implements TransactionRule {

    @Override
    public void apply(Account account, TransactionRequest request) {
        if (!account.getActive()) {
            throw new TransactionRejectedException("cartao-inativo");
        }
    }
}
