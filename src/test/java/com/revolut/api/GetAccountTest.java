/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.api;

import com.revolut.common.APICall;
import com.revolut.common.InitEnvironment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: getAccountTest.java, v 0.1
 */
public class GetAccountTest {

    private final static String urlAccount = "http://localhost:7000/account";

    private final static String EXPECT_RESULT_GET = "{\"balance\":300.00,\"id\":\"4\"}";
    @Before
    public void initApp(){
        InitEnvironment.initApp();
    }
    @Test
    public void createAndGet() {
        try {
            APICall.callPostAPI(urlAccount, APICall.accountBody("4","300"));
            var response = APICall.callGetAPI(urlAccount + "/4");
            Assert.assertTrue(response.statusCode() == 200);
            Assert.assertTrue(response.body().contains("\"balance\":300.00"));
            Assert.assertTrue(response.body().contains("\"id\":\"4\""));
        } catch (IOException e) {
            Assert.fail();
        } catch (InterruptedException e) {
            Assert.fail();
        }
    }

    @Test
    public void createAndGetAll() {
        try {
            APICall.callPostAPI(urlAccount, APICall.accountBody("5","300"));
            APICall.callPostAPI(urlAccount, APICall.accountBody("6","300"));
            var response = APICall.callGetAPI(urlAccount);
            Assert.assertTrue(response.statusCode() == 200);
            Assert.assertTrue(response.body().contains("{\"balance\":300.00,\"id\":\"5\"}"));
            Assert.assertTrue(response.body().contains("{\"balance\":300.00,\"id\":\"6\"}"));
        } catch (IOException e) {
            Assert.fail();
        } catch (InterruptedException e) {
            Assert.fail();
        }
    }
}