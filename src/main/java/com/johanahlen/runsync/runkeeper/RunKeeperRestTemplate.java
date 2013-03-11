package com.johanahlen.runsync.runkeeper;

import java.io.IOException;
import java.io.InputStreamReader;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;

public class RunKeeperRestTemplate {

    HttpTransport transport;
    Gson gson;

    @Value("${runkeeper.accesstoken}")
    private String accessToken;

    public RunKeeperRestTemplate() {
        this.transport = new ApacheHttpTransport();
        this.gson = new Gson();
    }

    public <T> T executeGet(String url, Class<T> expectedType) throws IOException {
        GenericUrl genericUrl = new GenericUrl(url);
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(this.accessToken);
        final HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        HttpResponse response = requestFactory.buildGetRequest(genericUrl).execute();
        return gson.fromJson(new InputStreamReader(response.getContent()), expectedType);
    }

    public String executeGet(String url) throws IOException {
        GenericUrl genericUrl = new GenericUrl(url);
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(this.accessToken);
        final HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        HttpResponse response = requestFactory.buildGetRequest(genericUrl).execute();
        InputStreamReader inputStreamReader = new InputStreamReader(response.getContent());
        int val;
        StringBuilder builder = new StringBuilder();
        while ((val = inputStreamReader.read()) != -1) {
            builder.append((char) val);
        }
        return builder.toString();
    }

    public void executePost(String url, String contentType, Object data) throws IOException {
        GenericUrl genericUrl = new GenericUrl(url);
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(this.accessToken);
        final HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        HttpContent httpContent = new ByteArrayContent(contentType, gson.toJson(data).getBytes());
        requestFactory.buildPostRequest(genericUrl, httpContent).execute();
    }

    public void executeDelete(String url) throws IOException {
        GenericUrl genericUrl = new GenericUrl(url);
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(this.accessToken);
        final HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        requestFactory.buildDeleteRequest(genericUrl).execute();
    }
}
