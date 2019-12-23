/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.hsqldb.InMemoryDatabase;
import com.revolut.model.Account;
import com.revolut.model.Transaction;
import com.revolut.service.AccountService;
import com.revolut.service.AccountServiceImpl;
import com.revolut.service.TransactionService;
import com.revolut.service.TransactionServiceImpl;
import io.javalin.Javalin;

import java.util.List;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: MainApplication.java, v 0.1
 */
public class RestApplication {
    private static final int JAVALIN_PORT = 7000;

    public static void main(String[] args) {
        //Creating InMemory DB
        InMemoryDatabase myDb = new InMemoryDatabase();
        myDb.initializeTables();

        AccountService accountService = AccountServiceImpl.instance();
        accountService.init(myDb);

        TransactionService transactionService = TransactionServiceImpl.instance();
        transactionService.init(myDb);


        //Creating RestAPI's with Javalin Framework
        Javalin app = Javalin.create()
                .port(JAVALIN_PORT)
                .start();

        app.get("/", ctx -> ctx.result("WELCOME TO ALBERT PEREZ REVOLUT TEST"));

        app.post("/account", ctx -> {
            try {
                accountService.createAccount(ctx.bodyAsClass(Account.class));
                ctx.result(ctx.body());
            } catch (Exception e) {
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        });
        app.get("/account", ctx -> {
            try {
                List<Account> result = accountService.getAllAccounts();
                ObjectMapper mapper = new ObjectMapper();
                ctx.result(mapper.writeValueAsString(result));
            } catch (Exception e) {
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        });
        app.get("/account/:accountId", ctx -> {
            try {
                Account result = accountService.getAccountById(ctx.param("accountId"));
                ObjectMapper mapper = new ObjectMapper();
                ctx.result(mapper.writeValueAsString(result));
            } catch (Exception e) {
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        });

        app.get("/transaction", ctx -> {
            try {
                List<Transaction> result = transactionService.getTransactions();
                ObjectMapper mapper = new ObjectMapper();
                ctx.result(mapper.writeValueAsString(result));
            } catch (Exception e) {
                ctx.status(500);
                ctx.result(e.getMessage());
            }
        });

        app.post("/transaction", ctx -> {
            try {
                transactionService.transferMoney(ctx.bodyAsClass(Transaction.class));
                ctx.result(ctx.body());
            } catch (Exception e) {
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        });


    }
}