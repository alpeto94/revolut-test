/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.exception;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: NegativeAmountTransactionException.java, v 0.1
 */
public class NegativeAmountTransactionException extends Exception {
    private static final String MESSAGE = "The amount of money to transfer needs to be positive";
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}