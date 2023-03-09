package crs.projects.mockbank.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    private Double balance;

    private Boolean isActive;

    private Long userId;

    public Account() {

    }

    public Account(Long id, String name, AccountType type, Double balance, Boolean isActive, Long userId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.isActive = isActive;
        this.userId = userId;
    }
}
