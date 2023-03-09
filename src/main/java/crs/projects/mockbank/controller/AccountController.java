package crs.projects.mockbank.controller;

import crs.projects.mockbank.dto.AccountDto;
import crs.projects.mockbank.model.Account;
import crs.projects.mockbank.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/users/{userId}/accounts")
    public List<AccountDto> findAccountsByUserId(@PathVariable Long userId) {
        List<Account> accounts = accountService.findByUserId(userId);
        List<AccountDto> accountDtos = accounts.stream().map(AccountDto::fromEntity).toList();
        return accountDtos;
    }

    @PostMapping("/accounts")
    public AccountDto createAccount(@RequestBody AccountDto accountDto) {
        Account account = accountDto.toEntity();
        Account savedAccount = accountService.save(account);
        AccountDto responseAccountDto = AccountDto.fromEntity(savedAccount);
        return responseAccountDto;
    }

    @PutMapping("/accounts")
    public AccountDto updateAccount(@RequestBody AccountDto accountDto) {
        Account account = accountDto.toEntity();
        Account savedAccount = accountService.save(account);
        AccountDto responseAccountDto = AccountDto.fromEntity(savedAccount);
        return responseAccountDto;
    }

    @DeleteMapping("/accounts/{accountId}")
    public void deleteAccount(@PathVariable Long accountId) {
        accountService.delete(accountId);
    }
}
