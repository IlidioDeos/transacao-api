package br.com.ibmec.transacao_api.service;

import br.com.ibmec.transacao_api.dto.TransactionRequest;
import br.com.ibmec.transacao_api.dto.TransactionResponse;
import br.com.ibmec.transacao_api.exception.TransactionNotFoundException;
import br.com.ibmec.transacao_api.model.Account;
import br.com.ibmec.transacao_api.model.Transaction;
import br.com.ibmec.transacao_api.model.TransactionStatus;
import br.com.ibmec.transacao_api.repository.TransactionRepository;
import br.com.ibmec.transacao_api.service.rules.TransactionRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final List<TransactionRule> transactionRules;
    private final Clock clock;
    private final NotificationService notificationService; // Adicionado

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  AccountService accountService,
                                  List<TransactionRule> transactionRules,
                                  Clock clock,
                                  NotificationService notificationService) { // Adicionado
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionRules = transactionRules;
        this.clock = clock;
        this.notificationService = notificationService; // Adicionado
    }

    @Override
    @Transactional
    public TransactionResponse authorizeTransaction(TransactionRequest request) throws AccountNotFoundException {
        logger.info("Autorizando transação para o cartão: {}", request.getCardNumber());

        Account account = accountService.getAccountByCardNumber(request.getCardNumber());

        // Executar todas as regras
        for (TransactionRule rule : transactionRules) {
            rule.apply(account, request);
        }

        // Se todas as regras passarem, aprovar a transação
        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .merchant(request.getMerchant())
                .timestamp(LocalDateTime.now(clock))
                .account(account)
                .status(TransactionStatus.APPROVED)
                .build();

        // Deduzir o valor da transação do limite disponível
        account.setAvailableLimit(account.getAvailableLimit().subtract(request.getAmount()));
        // Salvar a conta atualizada
        accountService.saveAccount(account);
        // Salvar a transação
        Transaction savedTransaction = transactionRepository.save(transaction);

        logger.info("Transação aprovada: {}", savedTransaction.getId());

        // Enviar notificação
        notificationService.sendTransactionNotification("cliente@example.com", savedTransaction.getId());

        return TransactionResponse.fromEntity(savedTransaction);
    }

    @Override
    @Transactional
    public void notifyTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transação não encontrada."));

        // Lógica de notificação, por exemplo, enviar email, webhook, etc.
        logger.info("Notificando transação: {}", transactionId);

        // Exemplo: Enviar um e-mail de notificação
        notificationService.sendTransactionNotification("cliente@example.com", transactionId);
    }
}
