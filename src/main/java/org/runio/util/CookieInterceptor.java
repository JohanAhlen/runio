package org.runio.util;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class CookieInterceptor implements ClientHttpRequestInterceptor{

    private static final String COOKIE_HEADER = "Cookie";
    private static final String SET_COOKIE_HEADER = "Set-Cookie";

    private final Set<String> cookies = new ConcurrentSkipListSet<String>();

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        for(String cookie : cookies) {
            request.getHeaders().add(COOKIE_HEADER, cookie);
        }
        ClientHttpResponse response = execution.execute(request, body);
        if (response.getHeaders().containsKey(SET_COOKIE_HEADER)) {
            cookies.addAll(response.getHeaders().get(SET_COOKIE_HEADER));
        }
        return execution.execute(request, body);
    }
}
