package crs.projects.mockbank.service.impl;

import crs.projects.mockbank.dto.AccountTransferDto;
import crs.projects.mockbank.error.EntityNotFoundException;
import crs.projects.mockbank.model.Account;
import crs.projects.mockbank.model.Transaction;
import crs.projects.mockbank.model.TransactionType;
import crs.projects.mockbank.repository.AccountRepository;
import crs.projects.mockbank.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionServiceImpl transactionService;

    public Account findById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(EntityNotFoundException::new);
    }

    public List<Account> findByUserId(Long userId) {
        return accountRepository.findAccountsByUserId(userId);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void delete(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(EntityNotFoundException::new);
        accountRepository.delete(account);
    }

    @Transactional
    public void transfer(AccountTransferDto accountTransferDto) {
        List<Account> userAccounts = accountRepository.findAccountsByUserId(accountTransferDto.getUserId());
        Account fromAccount = userAccounts
                .stream()
                .filter(account -> Objects.equals(account.getId(), accountTransferDto.getFromAccountId()))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);

        Account toAccount = userAccounts
                .stream()
                .filter(account -> Objects.equals(account.getId(), accountTransferDto.getToAccountId()))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);

        if (accountTransferDto.getFromAccountId().equals(accountTransferDto.getToAccountId())) {
            throw new RuntimeException("Can not transfer money to same account it's being transferred from.");
        }

        if (fromAccount.getBalance() >= accountTransferDto.getAmount()) {
            Transaction fromTransaction = Transaction.builder()
                    .account(Account.builder().id(accountTransferDto.getFromAccountId()).build())
                    .type(TransactionType.DEBIT)
                    .amount(accountTransferDto.getAmount())
                    .description(String.format("Transfer to account %s", toAccount.getName()))
                    .timestamp(Instant.now())
                    .build();

            Transaction toTransaction = Transaction.builder()
                    .account(Account.builder().id(accountTransferDto.getToAccountId()).build())
                    .type(TransactionType.CREDIT)
                    .amount(accountTransferDto.getAmount())
                    .description(String.format("Transfer from account %s", toAccount.getName()))
                    .timestamp(Instant.now())
                    .build();


            transactionService.create(fromTransaction);
            transactionService.create(toTransaction);
        } else {
            throw new RuntimeException("Insufficient funds ");
        }

    }
}
