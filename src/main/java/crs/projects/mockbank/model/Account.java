package crs.projects.mockbank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    private Double balance;

    private Boolean isActive;

    private Boolean isOverdraftAllowed;

    private Double overdraftAmountLimit;

    private Double overdraftFeeAmount;

    private Long userId;

}
