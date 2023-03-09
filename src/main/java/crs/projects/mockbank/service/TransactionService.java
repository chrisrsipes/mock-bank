package crs.projects.mockbank.service;


import crs.projects.mockbank.model.Account;
import crs.projects.mockbank.model.Transaction;
import crs.projects.mockbank.model.TransactionType;
import crs.projects.mockbank.repository.AccountRepository;
import crs.projects.mockbank.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        Optional<Account> maybeAccount = accountRepository.findById(transaction.getAccount().getId());


        if (transaction.getAmount() < 0) {
            throw new RuntimeException("Negative numbers are not allowed for amount");
        }

        if (transaction.getTimestamp() == null) {
            transaction.setTimestamp(Instant.now());
        }

        if (maybeAccount.isPresent()) {
            Account account = maybeAccount.get();
            Double newBalance;

            if (transaction.getType().equals(TransactionType.CREDIT)) {
                newBalance = account.getBalance() + transaction.getAmount();
            } else {
                if (account.getBalance() < transaction.getAmount()) {
                    if (account.getIsOverdraftAllowed() != null && account.getIsOverdraftAllowed().equals(true) && transaction.getAmount() - account.getBalance() <= account.getOverdraftAmountLimit()) {
                        newBalance = account.getBalance() - transaction.getAmount() - account.getOverdraftFeeAmount();

                        Transaction overdraftTransaction = Transaction.builder()
                                .type(TransactionType.DEBIT)
                                .amount(account.getOverdraftFeeAmount())
                                .timestamp(Instant.now())
                                .account(Account.builder().id(account.getId()).build())
                                .build();

                        transactionRepository.save(overdraftTransaction);
                    } else {
                        throw new RuntimeException("Transaction would exceed overdraft limit for the account.");
                    }
                } else {
                    newBalance = account.getBalance() - transaction.getAmount();
                }

            }

            account.setBalance(newBalance);
            accountRepository.save(account);
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
