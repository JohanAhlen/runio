package org.runio.garmin;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;

import java.io.*;
import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizingHttpClientRequestFactory implements ClientHttpRequestFactory {

    private static final Logger log = LoggerFactory.getLogger(AuthorizingHttpClientRequestFactory.class);

    private static final String signInPage = "https://sso.garmin.com/sso/login?" +
            "service=http%3A%2F%2Fconnect.garmin.com%2Fpost-auth%2Flogin&" +
            "webhost=olaxpw-connect05.garmin.com&" +
            "source=http%3A%2F%2Fconnect.garmin.com%2Fen-US%2Fsignin&" +
            "redirectAfterAccountLoginUrl=http%3A%2F%2Fconnect.garmin.com%2Fpost-auth%2Flogin&" +
            "redirectAfterAccountCreationUrl=http%3A%2F%2Fconnect.garmin.com%2Fpost-auth%2Flogin&" +
            "gauthHost=https%3A%2F%2Fsso.garmin.com%2Fsso&" +
            "locale=en&" +
            "id=gauth-widget&" +
            "cssUrl=https%3A%2F%2Fstatic.garmincdn.com%2Fcom.garmin.connect%2Fui%2Fsrc-css%2Fgauth-custom.css&" +
            "clientId=GarminConnect&" +
            "rememberMeShown=true&" +
            "rememberMeChecked=false&" +
            "createAccountShown=true&" +
            "openCreateAccount=false&" +
            "usernameShown=true&" +
            "displayNameShown=false&" +
            "consumeServiceTicket=false&" +
            "initialFocus=true&" +
            "embedWidget=false";


    private static final String loginFormPostUrl = "https://sso.garmin.com/sso/login?" +
            "service=http%3A%2F%2Fconnect.garmin.com%2Fpost-auth%2Flogin&" +
            "webhost=olaxpw-connect05.garmin.com&" +
            "source=http%3A%2F%2Fconnect.garmin.com%2Fen-US%2Fsignin&" +
            "redirectAfterAccountLoginUrl=http%3A%2F%2Fconnect.garmin.com%2Fpost-auth%2Flogin&" +
            "redirectAfterAccountCreationUrl=http%3A%2F%2Fconnect.garmin.com%2Fpost-auth%2Flogin&" +
            "gauthHost=https%3A%2F%2Fsso.garmin.com%2Fsso&" +
            "locale=en&" +
            "id=gauth-widget&" +
            "cssUrl=https%3A%2F%2Fstatic.garmincdn.com%2Fcom.garmin.connect%2Fui%2Fsrc-css%2Fgauth-custom.css&" +
            "clientId=GarminConnect&" +
            "rememberMeShown=true&" +
            "rememberMeChecked=false&" +
            "createAccountShown=true&" +
            "openCreateAccount=false&" +
            "usernameShown=true&" +
            "displayNameShown=false&" +
            "consumeServiceTicket=false&" +
            "initialFocus=true&" +
            "embed=true&" +
            "_eventId=submit&" +
            "displayNameRequired=false&" +
            "embedWidget=false";

    @Value("${garmin.password}")
    private String password;

    @Value("${garmin.username}")
    private String username;

    private AtomicBoolean signedIn = new AtomicBoolean(false);
    private CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    @Override
    public ClientHttpRequest createRequest(final URI uri, HttpMethod httpMethod) throws IOException {
        if (!signedIn.get()) {
            authorise();
        }
        switch (httpMethod) {
            case GET:
                final HttpUriRequest get = new HttpGet(uri);
                return createClientHttpRequest(uri, get, HttpMethod.GET);
            case POST:
                final HttpUriRequest post = new HttpPost(uri);
                return createClientHttpRequest(uri, post, HttpMethod.POST);
            default:
                throw new UnsupportedOperationException("HttpMethod " + httpMethod + " not implemented");
        }
    }


    public void authorise() throws IOException {
        if (signedIn.get()) {
            return;
        }
        log.info("Retrieving Garmin SSO sign-in page.");
        HttpGet getForSignInPage = new HttpGet(signInPage);
        CloseableHttpResponse signInPageResponse = httpClient.execute(getForSignInPage);

        log.info("Posting sign-in form.");
        String enrichedLoginFormPostUrl = loginFormPostUrl + "&username=" + username + "&password=" + password + "&lt=" + getFlowKeyFromResponse(signInPageResponse);
        CloseableHttpResponse signInFormPostResponse = httpClient.execute(new HttpPost(enrichedLoginFormPostUrl));

        HttpGet getForPostAuth = new HttpGet(getPostAuthUrl(signInFormPostResponse));
        httpClient.execute(getForPostAuth);
        log.info("Authenticated with Garmin SSO.");

        signedIn.set(true);
    }

    private String getPostAuthUrl(CloseableHttpResponse response) throws IOException {
        return getRegexGroupFromResponseContent(response, ".*var response_url[^=]*=[^']*'([^']+)';.*");
    }

    private String getFlowKeyFromResponse(CloseableHttpResponse response) throws IOException {
        return getRegexGroupFromResponseContent(response, ".*flowExecutionKey: \\[([a-z0-9]*)\\].*");
    }

    private String getRegexGroupFromResponseContent(CloseableHttpResponse response, String regex) throws IOException {
        String content = getStringContent(response);

        Matcher flowKeyMatcher = Pattern.compile(regex).matcher(content);
        if (!flowKeyMatcher.find()) {
            throw new RuntimeException("No matching group found for regex: " + regex);
        }
        return flowKeyMatcher.group(1);
    }

    private String getStringContent(CloseableHttpResponse response) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
        return writer.toString();
    }


    private ClientHttpRequest createClientHttpRequest(final URI uri, final HttpUriRequest request, final HttpMethod method) {
        return new ClientHttpRequest() {
            @Override
            public ClientHttpResponse execute() throws IOException {
                final CloseableHttpResponse response = httpClient.execute(request);
                return adaptToClientHttpResponse(response);
            }

            @Override
            public OutputStream getBody() throws IOException {
                return new ByteArrayOutputStream(0);
            }

            @Override
            public HttpMethod getMethod() {
                return method;
            }

            @Override
            public URI getURI() {
                return uri;
            }

            @Override
            public HttpHeaders getHeaders() {
                return new HttpHeaders();
            }
        };
    }

    private ClientHttpResponse adaptToClientHttpResponse(final CloseableHttpResponse response) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.valueOf(response.getStatusLine().getStatusCode());
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return response.getStatusLine().getStatusCode();
            }

            @Override
            public String getStatusText() throws IOException {
                return response.getStatusLine().getReasonPhrase();
            }

            @Override
            public void close() {
                try {
                    response.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public InputStream getBody() throws IOException {
                return response.getEntity().getContent();
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                for (Header header : response.getAllHeaders()) {
                    headers.add(header.getName(), header.getValue());
                }
                return headers;
            }
        };
    }
}
