/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.common;

import com.revolut.rest.RestApplication;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: InitEnvironment.java, v 0.1
 */
public class InitEnvironment {

    private static boolean init = false;

    public static void initApp() {
        if (!init) {
            RestApplication.main(null);
            init=true;
        }
    }

}