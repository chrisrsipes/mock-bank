package crs.projects.mockbank.service.impl;


import crs.projects.mockbank.error.EntityNotFoundException;
import crs.projects.mockbank.model.Account;
import crs.projects.mockbank.model.Transaction;
import crs.projects.mockbank.model.TransactionType;
import crs.projects.mockbank.repository.AccountRepository;
import crs.projects.mockbank.repository.TransactionRepository;
import crs.projects.mockbank.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public Transaction create(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccount().getId()).orElseThrow(EntityNotFoundException::new);


        if (transaction.getAmount() < 0) {
            throw new RuntimeException("Negative numbers are not allowed for amount");
        }

        if (transaction.getTimestamp() == null) {
            transaction.setTimestamp(Instant.now());
        }

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
                            .description("Overdraft for transaction")
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

    }

    // @TODO: update this with business logic to prevent amount being updated; only updates should be allowed for metadata
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findByAccountId(Long accountId) {
        return transactionRepository.findTransactionsByAccountId(accountId);
    }

    public void delete(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(EntityNotFoundException::new);
        transactionRepository.delete(transaction);
    }
}
