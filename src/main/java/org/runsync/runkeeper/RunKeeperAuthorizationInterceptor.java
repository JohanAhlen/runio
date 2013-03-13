package org.runsync.runkeeper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RunKeeperAuthorizationInterceptor implements ClientHttpRequestInterceptor{

    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ACCEPT_HEADER = "Accept";
    private static final List<String> ACCEPT_VALUES = Arrays.asList(new String[] {
        "application/vnd.com.runkeeper.FitnessActivityFeed+json",
        "application/vnd.com.runkeeper.User+json"
    });

    @Value("${runkeeper.accesstoken}")
    private String accessToken;

    public RunKeeperAuthorizationInterceptor() {
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add(AUTHORIZATION_HEADER, BEARER_PREFIX + accessToken);
        request.getHeaders().put(ACCEPT_HEADER, ACCEPT_VALUES);
        ClientHttpResponse response = execution.execute(request, body);
        response.getHeaders().set("Content-Type", "application/json");
        return response;
    }
}
