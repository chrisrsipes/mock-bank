package crs.projects.mockbank.service;

import crs.projects.mockbank.model.Account;
import crs.projects.mockbank.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> findById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public List<Account> findByUserId(Long userId) {
        return accountRepository.findAccountsByUserId(userId);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void delete(Long accountId) {
        Optional<Account> maybeAccount = accountRepository.findById(accountId);

        // @TODO: do we want to give a 404 if entity could not be deleted?
        if (maybeAccount.isPresent()) {
            accountRepository.delete(maybeAccount.get());
        }
    }
}
