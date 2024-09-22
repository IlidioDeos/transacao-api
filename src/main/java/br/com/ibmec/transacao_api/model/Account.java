package br.com.ibmec.transacao_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private BigDecimal availableLimit;

    @Column(nullable = false)
    private Boolean active;

    @Version
    private Long version;

    // Outras informações relevantes, como cliente, etc.
}
