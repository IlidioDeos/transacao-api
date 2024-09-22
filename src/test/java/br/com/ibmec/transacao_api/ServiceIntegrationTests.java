package br.com.ibmec.transacao_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.ibmec.transacao_api.dto.CreateAccountRequest;
import br.com.ibmec.transacao_api.dto.TransactionRequest;
import br.com.ibmec.transacao_api.model.Account;
import br.com.ibmec.transacao_api.repository.AccountRepository;
import br.com.ibmec.transacao_api.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ServiceIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();

        Account account = Account.builder()
                .cardNumber("1234-5678-9012-3456")
                .availableLimit(new BigDecimal("1000.00"))
                .active(true)
                .build();

        accountRepository.save(account);
    }

    @Test
    void testCreateAccount() throws Exception {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setCardNumber("9876-5432-1098-7654");
        request.setAvailableLimit(new BigDecimal("5000.00"));

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cardNumber").value("9876-5432-1098-7654"))
                .andExpect(jsonPath("$.availableLimit").value(5000.00))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void testAuthorizeTransaction_Success() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setCardNumber("1234-5678-9012-3456");
        request.setAmount(new BigDecimal("100.00"));
        request.setMerchant("Mercado Livre");

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.merchant").value("Mercado Livre"))
                .andExpect(jsonPath("$.amount").value(100.00));
    }

    @Test
    void testAuthorizeTransaction_LimitInsufficient() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setCardNumber("1234-5678-9012-3456");
        request.setAmount(new BigDecimal("2000.00"));
        request.setMerchant("Mercado Livre");

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("limite-insuficiente"));
    }

    @Test
    void testAuthorizeTransaction_CardInactive() throws Exception {
        // Inativar o cartão
        Account account = accountRepository.findByCardNumber("1234-5678-9012-3456").orElseThrow();
        account.setActive(false);
        accountRepository.save(account);

        TransactionRequest request = new TransactionRequest();
        request.setCardNumber("1234-5678-9012-3456");
        request.setAmount(new BigDecimal("100.00"));
        request.setMerchant("Mercado Livre");

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("cartao-inativo"));
    }

    // Adicione mais testes para outras regras
}
