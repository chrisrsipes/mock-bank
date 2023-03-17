package crs.projects.mockbank.dto;

import crs.projects.mockbank.model.Account;
import crs.projects.mockbank.model.Transaction;
import crs.projects.mockbank.model.TransactionType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class TransactionDto {

    private Long id;

    private TransactionType type;

    private Double amount;

    private String description;

    private Instant timestamp = Instant.now();

    private Long accountId;

    public Transaction toEntity() {
        return Transaction.builder()
                .id(this.getId())
                .type(this.getType())
                .amount(this.getAmount())
                .description(this.getDescription())
                .timestamp(this.getTimestamp())
                .account(
                        Account
                                .builder()
                                .id(this.getAccountId())
                                .build()
                )
                .build();
    }

    public static TransactionDto fromEntity(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .timestamp(transaction.getTimestamp())
                .accountId(transaction.getAccount().getId())
                .build();
    }


}
