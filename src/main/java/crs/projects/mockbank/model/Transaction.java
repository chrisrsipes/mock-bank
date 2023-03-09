package crs.projects.mockbank.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Data
@Builder
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private Double amount;

    private Instant timestamp = Instant.now();

    @ManyToOne
    @JoinColumn(name = "account.id", nullable = false)
    private Account account;

    public Transaction() {

    }

    public Transaction(Long id, TransactionType type, Double amount, Instant timestamp, Account account) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.account = account;
    }
}
