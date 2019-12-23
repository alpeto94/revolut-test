/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.service;

import com.revolut.dao.TransactionDao;
import com.revolut.dao.TransactionDaoImpl;
import com.revolut.exception.AccountIdDoesNotExistException;
import com.revolut.exception.AutoTransferingMoneyException;
import com.revolut.exception.NegativeAmountTransactionException;
import com.revolut.exception.NotEnoughFundsException;
import com.revolut.hsqldb.InMemoryDatabase;
import com.revolut.model.Account;
import com.revolut.model.Transaction;
import org.jooq.tools.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.revolut.constants.RevolutConstants.REJECT;
import static com.revolut.constants.RevolutConstants.SUCCESS;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: TransactionController.java, v 0.1
 */
public class TransactionServiceImpl implements TransactionService {

    private static ConcurrentMap<String, Object> lockingAccounts = new ConcurrentHashMap<>();
    private static TransactionServiceImpl transactionService = null;
    private TransactionDao transactionDao = new TransactionDaoImpl();

    public static synchronized TransactionServiceImpl instance() {
        if (transactionService == null) {
            transactionService = new TransactionServiceImpl();
        }
        return transactionService;
    }

    @Override
    public void init(InMemoryDatabase myDb) {
        transactionDao.init(myDb);
    }

    @Override
    public void transferMoney(Transaction transaction) throws NotEnoughFundsException, AccountIdDoesNotExistException, NegativeAmountTransactionException, AutoTransferingMoneyException {
        try {
            checkTransactionParameters(transaction);
        } catch (Exception e) {
            throw e;
        }
        try {
            String firstLock;
            String secondLock;

            //Setting queue ordering to break deadlocks
            if (transaction.getDebtor().compareTo(transaction.getCreditor()) > 0) {
                firstLock = transaction.getDebtor();
                secondLock = transaction.getCreditor();
            } else {
                firstLock = transaction.getCreditor();
                secondLock = transaction.getDebtor();
            }
            lockingAccounts.putIfAbsent(firstLock, new Object());
            lockingAccounts.putIfAbsent(secondLock, new Object());

            synchronized (lockingAccounts.get(firstLock)) {
                synchronized (lockingAccounts.get(secondLock)) {
                    Account debtorAccount = AccountServiceImpl.instance().getAccountById(transaction.getDebtor());
                    Account creditorAccount = AccountServiceImpl.instance().getAccountById(transaction.getCreditor());
                    if (transaction.getAmount().compareTo(debtorAccount.getBalance()) > 0) {
                        throw new NotEnoughFundsException();
                    } else {
                        BigDecimal debtorFinalAmount = debtorAccount.getBalance().subtract(transaction.getAmount());
                        BigDecimal creditorFinalAmount = creditorAccount.getBalance().add(transaction.getAmount());
                        AccountServiceImpl.instance().transferAccountsMoney(debtorAccount.getId(), debtorFinalAmount, creditorAccount.getId(), creditorFinalAmount);
                        transaction.setStatus(SUCCESS);
                    }
                }
            }
        } catch (Exception e) {
            transaction.setStatus(REJECT);
            throw e;

        } finally {
            transactionDao.addTransaction(transaction);
        }
    }

    private void checkTransactionParameters(Transaction transaction) throws NegativeAmountTransactionException, AutoTransferingMoneyException {
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeAmountTransactionException();
        }
        if (StringUtils.equals(transaction.getCreditor(), transaction.getDebtor())) {
            throw new AutoTransferingMoneyException();
        }
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactionDao.getAllTransactions();
    }

}