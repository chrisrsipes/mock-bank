package crs.projects.mockbank.controller;

import crs.projects.mockbank.dto.TransactionDto;
import crs.projects.mockbank.model.Transaction;
import crs.projects.mockbank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/accounts/{accountId}/transactions")
    public List<TransactionDto> findTransactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.findByAccountId(accountId);
        List<TransactionDto> transactionDtos = transactions.stream().map(TransactionDto::fromEntity).toList();
        return transactionDtos;
    }

    @PostMapping("/transactions")
    public TransactionDto createTransaction(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = transactionDto.toEntity();
        Transaction savedTransaction = transactionService.create(transaction);
        TransactionDto savedTransactionDto = TransactionDto.fromEntity(savedTransaction);
        return savedTransactionDto;
    }

    @PutMapping("/transactions")
    public TransactionDto updateTransaction(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = transactionDto.toEntity();
        Transaction savedTransaction = transactionService.create(transaction);
        TransactionDto savedTransactionDto = TransactionDto.fromEntity(savedTransaction);
        return savedTransactionDto;
    }

    // @TODO: determine if delete transaction should be surfaced via API, users should not be able to delete transactions
    @DeleteMapping("/transactions/{transactionId}")
    public void deleteTransaction(@PathVariable Long transactionId) {
        transactionService.delete(transactionId);
    }
}
