package crs.projects.mockbank.dto;

import crs.projects.mockbank.model.Account;
import crs.projects.mockbank.model.AccountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
    private Long id;

    private String name;

    private AccountType type;

    private Double balance;

    private Boolean isActive;

    private Boolean isOverdraftAllowed;

    private Double overdraftAmountLimit;

    private Double overdraftFeeAmount;

    private Long userId;

    public Account toEntity() {
        return Account.builder()
                .id(this.getId())
                .name(this.getName())
                .type(this.getType())
                .balance(this.getBalance())
                .isActive(this.getIsActive())
                .isOverdraftAllowed(this.getIsOverdraftAllowed())
                .overdraftAmountLimit(this.getOverdraftAmountLimit())
                .overdraftFeeAmount(this.getOverdraftFeeAmount())
                .userId(this.getUserId())
                .build();
    }
    public static AccountDto fromEntity(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .name(account.getName())
                .type(account.getType())
                .balance(account.getBalance())
                .isActive(account.getIsActive())
                .isOverdraftAllowed(account.getIsOverdraftAllowed())
                .overdraftAmountLimit(account.getOverdraftAmountLimit())
                .overdraftFeeAmount(account.getOverdraftFeeAmount())
                .userId(account.getUserId())
                .build();
    }
}
