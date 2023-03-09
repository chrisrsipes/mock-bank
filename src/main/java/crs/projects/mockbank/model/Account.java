package crs.projects.mockbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    private String type;

    private Double balance;

    private Boolean isActive;

    private Long userId;

    public Account() {

    }

    public Account(Long id, String name, String type, Double balance, Boolean isActive, Long userId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.isActive = isActive;
        this.userId = userId;
    }
}
