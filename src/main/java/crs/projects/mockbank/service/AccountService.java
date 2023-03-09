package crs.projects.mockbank.service;

import crs.projects.mockbank.model.Account;
import crs.projects.mockbank.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findByUserId(Long userId) {
        return accountRepository.findAccountsByUserId(userId);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
}
