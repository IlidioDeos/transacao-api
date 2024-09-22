package br.com.ibmec.transacao_api.service;


import br.com.ibmec.transacao_api.model.Account;
import br.com.ibmec.transacao_api.dto.CreateAccountRequest;
import br.com.ibmec.transacao_api.dto.AccountResponse;

import javax.security.auth.login.AccountNotFoundException;

public interface AccountService {
    AccountResponse createAccount(CreateAccountRequest request);
    Account getAccountByCardNumber(String cardNumber) throws AccountNotFoundException;
    Account saveAccount(Account account);
}

