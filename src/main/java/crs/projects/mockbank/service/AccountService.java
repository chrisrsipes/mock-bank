package crs.projects.mockbank.service;

import crs.projects.mockbank.dto.AccountTransferDto;
import crs.projects.mockbank.model.Account;
import crs.projects.mockbank.model.Transaction;
import crs.projects.mockbank.model.TransactionType;
import crs.projects.mockbank.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;

    public AccountService(AccountRepository accountRepository, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
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

    @Transactional
    public void transfer(AccountTransferDto accountTransferDto) {
        List<Account> userAccounts = accountRepository.findAccountsByUserId(accountTransferDto.getUserId());
        Optional<Account> fromAccount = userAccounts.stream().filter(account -> Objects.equals(account.getId(), accountTransferDto.getFromAccountId())).findFirst();
        Optional<Account> toAccount = userAccounts.stream().filter(account -> Objects.equals(account.getId(), accountTransferDto.getToAccountId())).findFirst();

        if (accountTransferDto.getFromAccountId().equals(accountTransferDto.getToAccountId())) {
            throw new RuntimeException("Can not transfer money to same account it's being transferred from.");
        }

        if (fromAccount.isPresent() && toAccount.isPresent()) {
            if (fromAccount.get().getBalance() >= accountTransferDto.getAmount()) {
                Transaction fromTransaction = Transaction.builder()
                        .account(Account.builder().id(accountTransferDto.getFromAccountId()).build())
                        .type(TransactionType.DEBIT)
                        .amount(accountTransferDto.getAmount())
                        .description(String.format("Transfer to account %s", toAccount.get().getName()))
                        .timestamp(Instant.now())
                        .build();

                Transaction toTransaction = Transaction.builder()
                        .account(Account.builder().id(accountTransferDto.getToAccountId()).build())
                        .type(TransactionType.CREDIT)
                        .amount(accountTransferDto.getAmount())
                        .description(String.format("Transfer from account %s", toAccount.get().getName()))
                        .timestamp(Instant.now())
                        .build();


                transactionService.create(fromTransaction);
                transactionService.create(toTransaction);
            } else {
                throw new RuntimeException("Insufficient funds ");
            }
        } else {
            throw new RuntimeException("User does not have the specified accounts");
        }
    }
}
