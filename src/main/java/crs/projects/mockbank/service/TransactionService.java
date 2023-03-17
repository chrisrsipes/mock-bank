package crs.projects.mockbank.service;


import crs.projects.mockbank.model.Transaction;
import java.util.List;

public interface TransactionService {

    Transaction create(Transaction transaction);

    Transaction save(Transaction transaction);

    List<Transaction> findByAccountId(Long accountId);

    void delete(Long transactionId);
}
