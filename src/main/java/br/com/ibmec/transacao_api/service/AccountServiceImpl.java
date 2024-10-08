package br.com.ibmec.transacao_api.service;

import br.com.ibmec.transacao_api.dto.AccountResponse;
import br.com.ibmec.transacao_api.dto.CreateAccountRequest;
import br.com.ibmec.transacao_api.exception.AccountAlreadyExistsException;
import br.com.ibmec.transacao_api.exception.AccountNotFoundException;
import br.com.ibmec.transacao_api.mapper.AccountMapper;
import br.com.ibmec.transacao_api.model.Account;
import br.com.ibmec.transacao_api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest request) {
        accountRepository.findByCardNumber(request.getCardNumber())
                .ifPresent(account -> {
                    throw new AccountAlreadyExistsException("Conta já existe para o cartão fornecido.");
                });

        Account account = accountMapper.toEntity(request);
        account.setActive(true); // Definindo a conta como ativa por padrão

        Account savedAccount = accountRepository.save(account);

        return accountMapper.toResponse(savedAccount);
    }

    @Override
    @Transactional
    public AccountResponse deactivateAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada com ID: " + accountId));

        if (!account.getActive()) {
            throw new IllegalStateException("Conta já está inativa.");
        }

        account.setActive(false);
        Account updatedAccount = accountRepository.save(account);

        return accountMapper.toResponse(updatedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccountByCardNumber(String cardNumber) {
        return accountRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada para o cartão fornecido."));
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada com ID: " + accountId));
    }

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }
}
