/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.model;

import java.math.BigDecimal;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: Transaction.java, v 0.1
 */
public class Transaction {
    private String debtor;
    private String creditor;
    private BigDecimal amount;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}