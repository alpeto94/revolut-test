/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.hsqldb;

import org.hsqldb.jdbc.JDBCPool;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import static com.revolut.constants.RevolutConstants.*;
import static org.jooq.impl.DSL.*;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: InMemoryDatabase.java, v 0.1
 */
public class InMemoryDatabase {
    private final DSLContext dslContext;

    public InMemoryDatabase() {
        JDBCPool pool = new JDBCPool();
        pool.setUrl(DEFAULT_URL);
        dslContext = DSL.using(pool, SQLDialect.HSQLDB);
    }

    public DSLContext ctx() {
        return dslContext;
    }


    public void initializeTables() {
        this.ctx().createTable(ACCOUNT)
                .column(ID, SQLDataType.LONGNVARCHAR.nullable(false))
                .column(BALANCE, SQLDataType.NUMERIC(20,2).nullable(false))
                .constraints(
                        constraint("PK_ACCOUNT")
                                .primaryKey(field(ID)),
                        constraint("POSITIVE_BALANCE")
                                .check(field(BALANCE).greaterOrEqual(0))
                ).execute();

        this.ctx().createTable(TRANSACTION)
                .column(DEBTOR_ID, SQLDataType.LONGNVARCHAR.nullable(false))
                .column(CREDITOR_ID, SQLDataType.LONGNVARCHAR.nullable(false))
                .column(AMOUNT, SQLDataType.DECIMAL(20,2).nullable(false))
                .column(STATUS, SQLDataType.LONGNVARCHAR.nullable(false))
                .constraints(
                        constraint("FOREIGN_KEY_DEBTOR")
                                .foreignKey(field(DEBTOR_ID))
                                .references(table(ACCOUNT), field(ID)),
                        constraint("FOREIGN_KEY_CREDITOR")
                                .foreignKey(field(CREDITOR_ID))
                                .references(table(ACCOUNT), field(ID)),
                        constraint("POSITIVE_AMOUNT")
                                .check(field(AMOUNT).greaterOrEqual(0))
                ).execute();
    }
}