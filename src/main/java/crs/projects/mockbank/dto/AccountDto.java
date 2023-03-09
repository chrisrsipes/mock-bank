package crs.projects.mockbank.dto;

import crs.projects.mockbank.model.Account;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
    private Long id;

    private String name;

    private String type;

    private Double balance;

    private Boolean isActive;

    private Long userId;

    public Account toEntity() {
        return Account.builder()
                .id(this.getId())
                .name(this.getName())
                .type(this.getType())
                .balance(this.getBalance())
                .isActive(this.getIsActive())
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
                .userId(account.getUserId())
                .build();
    }
}
