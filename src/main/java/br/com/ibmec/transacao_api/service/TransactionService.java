package br.com.ibmec.transacao_api.service;

import br.com.ibmec.transacao_api.dto.TransactionRequest;
import br.com.ibmec.transacao_api.dto.TransactionResponse;

import javax.security.auth.login.AccountNotFoundException;

public interface TransactionService {
    TransactionResponse authorizeTransaction(TransactionRequest request) throws AccountNotFoundException;
    void notifyTransaction(Long transactionId); // Adicionado
}
