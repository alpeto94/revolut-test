/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.api;

import com.revolut.common.APICall;
import com.revolut.common.InitEnvironment;
import com.revolut.common.TestConstants;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: AddAcoountTest.java, v 0.1
 */
public class AddAccountTest {
    @Before
    public void initApp() {
        InitEnvironment.initApp();
    }

    @Test
    public void addAccountTestSuccessfully() throws IOException, InterruptedException {
        final var response = APICall.callPostAPI(TestConstants.accountUrl, APICall.accountBody("1", "300"));
        assertTrue(response.statusCode() == 200);
        assertTrue(APICall.accountBody("1", "300").equals(response.body()));
    }

    @Test
    public void addAccountTwice() throws IOException, InterruptedException {
        try {
            APICall.callPostAPI(TestConstants.accountUrl, APICall.accountBody("2", "30"));
            final var response = APICall.callPostAPI(TestConstants.accountUrl, APICall.accountBody("2", "300"));
            assertTrue(response.statusCode() == 400);
            assertTrue("Can not create this account because already exists".equals(response.body()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addAccountInvalidParameters() {
        try {
            final var response = APICall.callPostAPI(TestConstants.accountUrl, APICall.accountBody("3", "-300"));
            assertTrue(response.statusCode() == 400);
            assertTrue(response.body().contains("POSITIVE_BALANCE"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}