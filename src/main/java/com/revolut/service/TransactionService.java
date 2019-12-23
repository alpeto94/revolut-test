/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.service;

import com.revolut.exception.AccountIdDoesNotExistException;
import com.revolut.exception.AutoTransferingMoneyException;
import com.revolut.exception.NegativeAmountTransactionException;
import com.revolut.exception.NotEnoughFundsException;
import com.revolut.hsqldb.InMemoryDatabase;
import com.revolut.model.Transaction;

import java.util.List;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: TransactionService.java, v 0.1
 */
public interface TransactionService {
    void init(InMemoryDatabase myDb);

    void transferMoney(Transaction transaction) throws NotEnoughFundsException, AccountIdDoesNotExistException, NegativeAmountTransactionException, AutoTransferingMoneyException;

    List<Transaction> getTransactions();

}