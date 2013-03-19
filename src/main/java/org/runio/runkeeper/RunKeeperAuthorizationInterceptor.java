package org.runio.runkeeper;

import java.io.IOException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RunKeeperAuthorizationInterceptor implements ClientHttpRequestInterceptor{

    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String BEARER_VALUE_PREFIX = "Bearer ";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    @Value("${runkeeper.accesstoken}")
    private String accessToken;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().setAccept(Collections.singletonList(MediaType.ALL));
        request.getHeaders().add(AUTHORIZATION_HEADER, BEARER_VALUE_PREFIX + accessToken);
        ClientHttpResponse response = execution.execute(request, body);
        response.getHeaders().set(CONTENT_TYPE, APPLICATION_JSON);
        return response;
    }
}
