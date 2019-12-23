/**
 * Revolut-Test
 * No copyright associated
 */
package com.revolut.common;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * @author Albert Perez <albertpereztoro@gmail.com>
 * @version $Id: APICall.java, v 0.1
 */
public class APICall {


    public static HttpResponse<String> callPostAPI(String uri, String body) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(URI.create(uri)).build();

        var client = buildHttpClient();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> callGetAPI(String uri) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri)).build();

        var client = buildHttpClient();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpClient buildHttpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(10))
                .proxy(ProxySelector.getDefault())
                .build();
    }

    public static String accountBody(String id, String balance) {
        return "{\n" +
                "\t\t\"balance\": " + balance + ",\n" +
                "\t\t\"id\": \"" + id + "\"\t\n" +
                "}";
    }

    public static String transactionBody(String debtor, String creditor, String amount) {
        return "{\n" +
                "\t\t\"debtor\": \"" + debtor + "\",\n" +
                "\t\t\"creditor\": \"" + creditor + "\",\n" +
                "\t\t\"amount\": "+ amount + "\n" +
                "}";
    }
}