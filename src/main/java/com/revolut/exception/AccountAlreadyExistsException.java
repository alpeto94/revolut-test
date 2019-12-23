/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.exception;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: AccountAlreadyExistsException.java, v 0.1
 */
public class AccountAlreadyExistsException extends Exception {
    private static final String MESSAGE = "Can not create this account because already exists";
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}