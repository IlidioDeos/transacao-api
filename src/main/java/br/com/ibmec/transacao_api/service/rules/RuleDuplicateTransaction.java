package br.com.ibmec.transacao_api.service.rules;

import br.com.ibmec.transacao_api.dto.TransactionRequest;
import br.com.ibmec.transacao_api.exception.TransactionRejectedException;
import br.com.ibmec.transacao_api.model.Account;
import br.com.ibmec.transacao_api.model.Transaction;
import br.com.ibmec.transacao_api.repository.TransactionRepository;
import br.com.ibmec.transacao_api.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class RuleDuplicateTransaction implements TransactionRule {

    private final TransactionRepository transactionRepository;
    private final Clock clock;

    @Autowired
    public RuleDuplicateTransaction(TransactionRepository transactionRepository, Clock clock) {
        this.transactionRepository = transactionRepository;
        this.clock = clock;
    }

    @Override
    public void apply(Account account, TransactionRequest request) {
        LocalDateTime twoMinutesAgo = TimeUtils.getTimeBefore(2, clock);
        List<Transaction> similarTransactions = transactionRepository.findByAccountAndAmountAndMerchantAndTimestampAfter(
                account,
                request.getAmount(),
                request.getMerchant(),
                twoMinutesAgo
        );
        if (similarTransactions.size() >= 2) {
            throw new TransactionRejectedException("transacao-duplicada");
        }
    }
}
