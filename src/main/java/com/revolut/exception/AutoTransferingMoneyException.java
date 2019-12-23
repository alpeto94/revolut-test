/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.exception;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: AutoTransferingMoneyException.java, v 0.1
 */
public class AutoTransferingMoneyException extends Exception {
    private static final String MESSAGE = "Can not be transferred money to the same account";
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}