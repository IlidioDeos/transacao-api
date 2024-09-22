package br.com.ibmec.transacao_api;



import br.com.ibmec.transacao_api.model.Account;
import br.com.ibmec.transacao_api.model.Transaction;
import br.com.ibmec.transacao_api.model.TransactionStatus;
import br.com.ibmec.transacao_api.repository.AccountRepository;
import br.com.ibmec.transacao_api.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TransacaoApiApplicationTests {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	private Account testAccount;

	@BeforeEach
	void setUp() {
		transactionRepository.deleteAll();
		accountRepository.deleteAll();

		testAccount = Account.builder()
				.cardNumber("1234-5678-9012-3456")
				.availableLimit(new BigDecimal("1000.00"))
				.active(true)
				.build();

		accountRepository.save(testAccount);
	}

	@Test
	void testCreateAndRetrieveAccount() {
		Optional<Account> retrievedAccount = accountRepository.findByCardNumber("1234-5678-9012-3456");
		assertThat(retrievedAccount).isPresent();
		assertThat(retrievedAccount.get().getAvailableLimit()).isEqualByComparingTo("1000.00");
	}

	@Test
	void testCreateAndRetrieveTransaction() {
		Transaction transaction = Transaction.builder()
				.amount(new BigDecimal("100.00"))
				.merchant("Mercado Livre")
				.timestamp(LocalDateTime.now())
				.account(testAccount)
				.status(TransactionStatus.APPROVED)
				.build();

		transactionRepository.save(transaction);

		List<Transaction> transactions = transactionRepository.findByAccountAndTimestampAfter(
				testAccount,
				LocalDateTime.now().minusMinutes(5)
		);

		assertThat(transactions).hasSize(1);
		assertThat(transactions.get(0).getMerchant()).isEqualTo("Mercado Livre");
	}
}
