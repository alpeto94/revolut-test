/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.dao;

import com.revolut.exception.AccountIdDoesNotExistException;
import com.revolut.hsqldb.InMemoryDatabase;
import com.revolut.model.Transaction;

import java.util.List;

import static com.revolut.constants.RevolutConstants.*;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: TransactionDaoImpl.java, v 0.1
 */
public class TransactionDaoImpl implements TransactionDao {
    private static TransactionDao transactionDao = null;
    private InMemoryDatabase myDb;

    public static synchronized TransactionDao instance() {
        if (transactionDao == null) {
            transactionDao = new TransactionDaoImpl();
        }
        return transactionDao;
    }

    public void init(InMemoryDatabase myDb) {
        this.myDb = myDb;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionRecord;
        transactionRecord = myDb.ctx().selectFrom(TRANSACTION)
                .fetchInto(Transaction.class);
        return transactionRecord;
    }

    @Override
    public void addTransaction(Transaction transaction) throws AccountIdDoesNotExistException {
        try {
            myDb.ctx().insertInto(table(TRANSACTION))
                    .set(field(STATUS), transaction.getStatus())
                    .set(field(AMOUNT), transaction.getAmount())
                    .set(field(CREDITOR_ID), transaction.getCreditor())
                    .set(field(DEBTOR_ID), transaction.getDebtor())
                    .execute();
        } catch (Exception e) {
            if (e.getMessage().contains("FOREIGN_KEY_CREDITOR") || e.getMessage().contains("FOREIGN_KEY_DEBTOR")) {
                throw new AccountIdDoesNotExistException();
            }
            throw e;
        }
    }
}
