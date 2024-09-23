package br.com.ibmec.transacao_api.service;


import br.com.ibmec.transacao_api.model.Account;
import br.com.ibmec.transacao_api.dto.CreateAccountRequest;
import br.com.ibmec.transacao_api.dto.AccountResponse;

import javax.security.auth.login.AccountNotFoundException;

public interface AccountService {
    AccountResponse createAccount(CreateAccountRequest request);
    AccountResponse deactivateAccount(Long accountId);
    Account getAccountByCardNumber(String cardNumber);
    Account getAccountById(Long accountId); // Para facilitar testes e outras operações
    Account saveAccount(Account account);
}

