/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.dao;

import com.revolut.exception.AccountAlreadyExistsException;
import com.revolut.exception.AccountIdDoesNotExistException;
import com.revolut.hsqldb.InMemoryDatabase;
import com.revolut.model.Account;
import org.hsqldb.HsqlException;

import java.math.BigDecimal;
import java.util.List;

import static com.revolut.constants.RevolutConstants.*;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: AccountDaoImpl.java, v 0.1
 */
public class AccountDaoImpl implements AccountDao {

    private static AccountDao accountDao = null;

    private InMemoryDatabase myDb;

    public static synchronized AccountDao instance() {
        if (accountDao == null) {
            accountDao = new AccountDaoImpl();
        }
        return accountDao;
    }

    public void init(InMemoryDatabase myDb) {
        this.myDb = myDb;
    }


    public Account getAccountById(String id) throws AccountIdDoesNotExistException, HsqlException {

        Account accountRecord;
        try {
            accountRecord = myDb.ctx().selectFrom(ACCOUNT)
                    .where(field(ID).eq(id))
                    .fetchOneInto(Account.class);
            if(accountRecord==null){
                throw new AccountIdDoesNotExistException();
            }
        } catch (Exception e) {
            throw e;
        }
        return accountRecord;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accountRecord;
        try {
            accountRecord = myDb.ctx().select()
                    .from(ACCOUNT).fetchInto(Account.class);
        } catch (Exception e) {
            throw e;
        }
        return accountRecord;
    }

    @Override
    public void updateAccounts(String debtor, BigDecimal debtorBalance, String creditor, BigDecimal creditorBalance) {
        try {
            myDb.ctx().transaction(configuration -> {
                myDb.ctx().update(table(ACCOUNT))
                        .set(field(BALANCE), debtorBalance)
                        .where(field(ID).eq(debtor))
                        .execute();

                myDb.ctx().update(table(ACCOUNT))
                        .set(field(BALANCE), creditorBalance)
                        .where(field(ID).eq(creditor))
                        .execute();
            });
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public synchronized void addAccount(Account account) throws AccountAlreadyExistsException {
        try {
            myDb.ctx().insertInto(table(ACCOUNT))
                    .set(field(ID), account.getId())
                    .set(field(BALANCE), account.getBalance())
                    .execute();
        } catch (Exception e) {
            if(e.getMessage().contains("PK_ACCOUNT")){
                throw new AccountAlreadyExistsException();
            }
            else {
                throw e;
            }
        }
    }
}