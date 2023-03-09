package crs.projects.mockbank.dto;

import crs.projects.mockbank.model.Account;
import lombok.Data;

@Data
public class AccountDto {
    private Long id;

    private String name;

    private String type;

    private Double balance;

    private Boolean isActive;

    private Long userId;

    public static AccountDto fromEntity(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setName(account.getName());
        accountDto.setType(account.getType());
        accountDto.setBalance(account.getBalance());
        accountDto.setIsActive(account.getIsActive());
        accountDto.setUserId(account.getUserId());

        return accountDto;
    }

    public Account toEntity() {
        Account account = new Account();
        account.setId(this.getId());
        account.setName(this.getName());
        account.setType(this.getType());
        account.setBalance(this.getBalance());
        account.setIsActive(this.getIsActive());
        account.setUserId(this.getUserId());

        return account;
    }
}
