package crs.projects.mockbank.service;

import crs.projects.mockbank.dto.AccountTransferDto;
import crs.projects.mockbank.model.Account;
import java.util.List;

public interface AccountService {

    Account findById(Long accountId);

    List<Account> findByUserId(Long userId);

    Account save(Account account);

    void delete(Long accountId);

    void transfer(AccountTransferDto accountTransferDto);
}
