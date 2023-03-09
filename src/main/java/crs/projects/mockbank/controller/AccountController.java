package crs.projects.mockbank.controller;

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
    public List<Account> findAccountsByUserId(@PathVariable Long userId) {
        return accountService.findByUserId(userId);
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account) {
        return accountService.save(account);
    }

    @PutMapping("/accounts")
    public Account updateAccount(@RequestBody Account account) {
        return accountService.save(account);
    }

    @DeleteMapping("/accounts/{accountId}")
    public void deleteAccount(@PathVariable Long accountId) {
        accountService.delete(accountId);
    }
}
