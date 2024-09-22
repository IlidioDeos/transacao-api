package br.com.ibmec.transacao_api.service.rules;

import br.com.ibmec.transacao_api.dto.TransactionRequest;
import br.com.ibmec.transacao_api.model.Account;

public interface TransactionRule {
    void apply(Account account, TransactionRequest request);
}
