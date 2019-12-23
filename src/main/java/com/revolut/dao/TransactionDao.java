/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.dao;

import com.revolut.exception.AccountIdDoesNotExistException;
import com.revolut.hsqldb.InMemoryDatabase;
import com.revolut.model.Transaction;

import java.util.List;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: TransactionDao.java, v 0.1
 */
public interface TransactionDao {

    void init(InMemoryDatabase myDb);
    List<Transaction> getAllTransactions();
    void addTransaction(Transaction transaction) throws AccountIdDoesNotExistException;


}