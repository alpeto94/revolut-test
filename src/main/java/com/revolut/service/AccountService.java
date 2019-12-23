/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.service;

import com.revolut.exception.AccountAlreadyExistsException;
import com.revolut.exception.AccountIdDoesNotExistException;
import com.revolut.hsqldb.InMemoryDatabase;
import com.revolut.model.Account;
import org.hsqldb.HsqlException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: AccountService.java, v 0.1
 */
public interface AccountService {
    void init (InMemoryDatabase myDb);
    void createAccount(Account account) throws AccountAlreadyExistsException;
    List<Account> getAllAccounts();
    Account getAccountById(String id) throws AccountIdDoesNotExistException, HsqlException;
    void transferAccountsMoney(String debtor, BigDecimal debtorBalance, String creditor, BigDecimal creditorBalance);
}