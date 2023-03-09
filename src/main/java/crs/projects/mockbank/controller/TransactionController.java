package crs.projects.mockbank.controller;

import crs.projects.mockbank.model.Account;
import crs.projects.mockbank.model.Transaction;
import crs.projects.mockbank.service.AccountService;
import crs.projects.mockbank.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public List<Transaction> findTransactionsByAccountId(@PathVariable Long accountId) {
        return transactionService.findByAccountId(accountId);
    }

    @PostMapping("/transactions")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.create(transaction);
    }

    @PutMapping("/transactions")
    public Transaction updateTransaction(@RequestBody Transaction transaction) {
        return transactionService.save(transaction);
    }

    // @TODO: determine if delete transaction should be surfaced via API, users should not be able to delete transactions
    @DeleteMapping("/transactions/{transactionId}")
    public void deleteTransaction(@PathVariable Long transactionId) {
        transactionService.delete(transactionId);
    }
}
