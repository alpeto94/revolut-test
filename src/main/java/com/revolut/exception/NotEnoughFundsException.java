/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.exception;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: NotEnoughFunds.java, v 0.1
 */
public class NotEnoughFundsException extends Exception {
    private static final String MESSAGE = "Not enough funds to do the transaction";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}