/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.dao;

import com.revolut.exception.AccountAlreadyExistsException;
import com.revolut.exception.AccountIdDoesNotExistException;
import com.revolut.hsqldb.InMemoryDatabase;
import com.revolut.model.Account;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: AccountDao.java, v 0.1
 */
public interface AccountDao {

    Account getAccountById(String id) throws AccountIdDoesNotExistException;
    void addAccount(Account account) throws AccountAlreadyExistsException;
    List<Account> getAllAccounts();
    void updateAccounts(String debtor, BigDecimal debtorBalance, String creditor, BigDecimal creditorBalance);
    void init(InMemoryDatabase myDb);

}