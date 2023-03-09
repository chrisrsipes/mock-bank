package crs.projects.mockbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private String type;

    private Double amount;

    private Instant timestamp = Instant.now();

    @ManyToOne
    @JoinColumn(name = "account.id", nullable = false)
    private Account account;
}
