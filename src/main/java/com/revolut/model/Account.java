/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.model;

import java.math.BigDecimal;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: Account.java, v 0.1
 */
public class Account{
    private BigDecimal balance;
    private String id;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId(){
        return id;
    }

}