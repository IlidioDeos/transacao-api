package br.com.ibmec.transacao_api.controller;

import br.com.ibmec.transacao_api.dto.AccountResponse;
import br.com.ibmec.transacao_api.dto.CreateAccountRequest;
import br.com.ibmec.transacao_api.model.Account;
import br.com.ibmec.transacao_api.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }

    @PutMapping("/{accountId}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse deactivateAccount(@PathVariable Long accountId) {
        return accountService.deactivateAccount(accountId);
    }

    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getAccountById(@PathVariable Long accountId) {
        Account account = accountService.getAccountById(accountId);
        return AccountResponse.fromEntity(account);
    }
}
