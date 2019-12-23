/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.exception;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: AccountIdDoesNotExistException.java, v 0.1
 */
public class AccountIdDoesNotExistException extends Exception {
    private static final String MESSAGE = "It does not exist an account with the ID specified";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}