package br.com.ibmec.transacao_api.repository;

import br.com.ibmec.transacao_api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByCardNumber(String cardNumber);
}
