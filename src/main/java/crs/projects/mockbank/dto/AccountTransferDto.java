package crs.projects.mockbank.dto;

import lombok.Data;

@Data
public class AccountTransferDto {
    private Long fromAccountId;

    private Long toAccountId;

    private Long userId;

    private Double amount;
}

