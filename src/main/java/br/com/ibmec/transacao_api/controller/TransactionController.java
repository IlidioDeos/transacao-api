package br.com.ibmec.transacao_api.controller;

import br.com.ibmec.transacao_api.dto.TransactionRequest;
import br.com.ibmec.transacao_api.dto.TransactionResponse;
import br.com.ibmec.transacao_api.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse authorizeTransaction(@Valid @RequestBody TransactionRequest request) throws AccountNotFoundException {
        return transactionService.authorizeTransaction(request);
    }

    @PostMapping("/{transactionId}/notify")
    @ResponseStatus(HttpStatus.OK)
    public void notifyTransaction(@PathVariable Long transactionId) {
        transactionService.notifyTransaction(transactionId);
    }
}

