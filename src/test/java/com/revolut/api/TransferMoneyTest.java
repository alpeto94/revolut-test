/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.api;

import com.revolut.common.APICall;
import com.revolut.common.InitEnvironment;
import com.revolut.common.TestConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: TransferMoneyTest.java, v 0.1
 */
public class TransferMoneyTest {
    @Before
    public void initApp() {
        InitEnvironment.initApp();
    }

    @Test
    public void transferMoneySingleThread() {
        try {
            APICall.callPostAPI(TestConstants.accountUrl, APICall.accountBody("7", "300"));
            APICall.callPostAPI(TestConstants.accountUrl, APICall.accountBody("8", "300"));
            var responseTransaction = APICall.callPostAPI(TestConstants.transactionUrl, APICall.transactionBody("7", "8", "3.333"));
            Assert.assertTrue(responseTransaction.statusCode() == 200);
            var responseGetAccounts = APICall.callGetAPI(TestConstants.accountUrl);
            Assert.assertTrue(responseGetAccounts.body().contains("{\"balance\":296.67,\"id\":\"7\"}"));
            Assert.assertTrue(responseGetAccounts.body().contains("{\"balance\":303.33,\"id\":\"8\"}"));
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void transferMoneySameAccount() {
        try {
            APICall.callPostAPI(TestConstants.accountUrl, APICall.accountBody("9", "300"));
            var response = APICall.callPostAPI(TestConstants.transactionUrl, APICall.transactionBody("9", "9", "3.333"));
            Assert.assertTrue(response.statusCode() == 400);
            Assert.assertTrue("Can not be transferred money to the same account".equals(response.body()));
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void transferNegativeAmount() {
        try {
            APICall.callPostAPI(TestConstants.accountUrl, APICall.accountBody("10", "300"));
            APICall.callPostAPI(TestConstants.accountUrl, APICall.accountBody("11", "300"));
            var response = APICall.callPostAPI(TestConstants.transactionUrl, APICall.transactionBody("10", "11", "-3.333"));
            Assert.assertTrue(response.statusCode() == 400);
            Assert.assertTrue("The amount of money to transfer needs to be positive".equals(response.body()));
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void notEnoughFoundsTest() {
        try {
            APICall.callPostAPI(TestConstants.accountUrl, APICall.accountBody("12", "300"));
            APICall.callPostAPI(TestConstants.accountUrl, APICall.accountBody("13", "300"));
            var response = APICall.callPostAPI(TestConstants.transactionUrl, APICall.transactionBody("12", "13", "3333"));
            Assert.assertTrue(response.statusCode() == 400);
            Assert.assertTrue("Not enough funds to do the transaction".equals(response.body()));
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void transferMoneyUnexistanceAccount() {
        try {
            var response = APICall.callPostAPI(TestConstants.transactionUrl, APICall.transactionBody("1232322A", "13232B", "3333"));
            Assert.assertTrue(response.statusCode() == 400);
            Assert.assertTrue("It does not exist an account with the ID specified".equals(response.body()));
        } catch (Exception e) {
            Assert.fail();
        }
    }

}