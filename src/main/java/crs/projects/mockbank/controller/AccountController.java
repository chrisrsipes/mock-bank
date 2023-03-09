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
    public List<Account> findByUserId(@PathVariable Long userId) {
        return accountService.findByUserId(userId);
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }
}
