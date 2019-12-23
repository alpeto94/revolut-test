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

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: MultithreadingTest.java, v 0.1
 */
public class MultithreadingTest {

    //Have chosen 100 threads because fulfill the purpose of testing deadlocks, race conditions... and the memory is not crashing
    private final static int threadCount = 100;

    @Before
    public void initApp() {
        InitEnvironment.initApp();
    }

    @Test
    public void nonRaceConditionTest() {
        try {

            APICall.callPostAPI(TestConstants.accountUrl, APICall.accountBody("albert", "3000"));
            APICall.callPostAPI(TestConstants.accountUrl, APICall.accountBody("revolut", "9999999"));
            Callable<HttpResponse<String>> task = new Callable<HttpResponse<String>>() {
                @Override
                public HttpResponse<String> call() throws IOException, InterruptedException {
                    APICall.callPostAPI(TestConstants.transactionUrl, APICall.transactionBody("albert", "revolut", "1"));
                    return APICall.callPostAPI(TestConstants.transactionUrl, APICall.transactionBody("revolut", "albert", "1"));
                }
            };

            List<Callable<HttpResponse<String>>> tasks = Collections.nCopies(threadCount, task);
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
            List<Future<HttpResponse<String>>> futures = executorService.invokeAll(tasks);
            List<HttpResponse<String>> resultList = new ArrayList<HttpResponse<String>>(futures.size());
            // Check for exceptions
            for (Future<HttpResponse<String>> future : futures) {
                // Throws an exception if an exception was thrown by the task.
                resultList.add(future.get());
            }
            // Validate the IDs
            Assert.assertEquals(threadCount, futures.size());

            var responseRevolut = APICall.callGetAPI(TestConstants.accountUrl + "/revolut");
            var responseAlbert = APICall.callGetAPI(TestConstants.accountUrl + "/albert");
            Assert.assertTrue(responseRevolut.body().contains("{\"balance\":9999999.00,\"id\":\"revolut\"}"));
            Assert.assertTrue(responseAlbert.body().contains("{\"balance\":3000.00,\"id\":\"albert\"}"));
        } catch (Exception e) {
            Assert.fail();
        }
    }
}

