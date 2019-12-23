/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.service;

import com.revolut.dao.AccountDao;
import com.revolut.dao.AccountDaoImpl;
import com.revolut.exception.AccountAlreadyExistsException;
import com.revolut.exception.AccountIdDoesNotExistException;
import com.revolut.hsqldb.InMemoryDatabase;
import com.revolut.model.Account;
import org.hsqldb.HsqlException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: AccountController.java, v 0.1
 */
public class AccountServiceImpl implements AccountService{

    private static AccountServiceImpl accountService = null;
    private AccountDao accountDao = new AccountDaoImpl();

    public static synchronized AccountServiceImpl instance() {
        if (accountService == null) {
            accountService = new AccountServiceImpl();
        }
        return accountService;
    }

    @Override
    public void init(InMemoryDatabase myDb){
        accountDao.init(myDb);
    }

    public void createAccount(Account account) throws AccountAlreadyExistsException {
        accountDao.addAccount(account);
    }

    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    public Account getAccountById(String id) throws AccountIdDoesNotExistException, HsqlException {
        return accountDao.getAccountById(id);
    }

    @Override
    public void transferAccountsMoney(String debtor, BigDecimal debtorBalance, String creditor, BigDecimal creditorBalance){
        accountDao.updateAccounts(debtor, debtorBalance,creditor,creditorBalance);
    }


}