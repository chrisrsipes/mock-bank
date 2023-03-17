package crs.projects.mockbank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
