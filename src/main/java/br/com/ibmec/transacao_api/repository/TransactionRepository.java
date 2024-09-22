package br.com.ibmec.transacao_api.repository;

import br.com.ibmec.transacao_api.model.Transaction;
import br.com.ibmec.transacao_api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountAndTimestampAfter(Account account, LocalDateTime timestamp);

    List<Transaction> findByAccountAndAmountAndMerchantAndTimestampAfter(Account account,
                                                                         BigDecimal amount,
                                                                         String merchant,
                                                                         LocalDateTime timestamp);
}
