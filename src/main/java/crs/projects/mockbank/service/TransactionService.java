package crs.projects.mockbank.service;


import crs.projects.mockbank.model.Account;
import crs.projects.mockbank.model.Transaction;
import crs.projects.mockbank.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;
    private AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        Optional<Account> maybeAccount = accountService.findById(transaction.getAccount().getId());

        if (transaction.getAmount() < 0) {
            throw new RuntimeException("Negative numbers are not allowed for amount");
        }

        if (maybeAccount.isPresent()) {
            Account account = maybeAccount.get();
            Double newBalance;
            if (transaction.getType().equals("Credit")) {
                newBalance = account.getBalance() + transaction.getAmount();
            } else {
                newBalance = account.getBalance() - transaction.getAmount();
            }

            account.setBalance(newBalance);
            accountService.save(account);
            return transactionRepository.save(transaction);
        } else {
            throw new RuntimeException("Account does not exist");
        }
    }

    // @TODO: update this with business logic to prevent amount being updated; only updates should be allowed for metadata
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findByAccountId(Long accountId) {
        return transactionRepository.findTransactionsByAccountId(accountId);
    }

    public void delete(Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            transactionRepository.delete(transaction.get());
        }
    }
}
