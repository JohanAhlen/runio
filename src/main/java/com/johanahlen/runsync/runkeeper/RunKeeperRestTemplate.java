package com.johanahlen.runsync.runkeeper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Scanner;
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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;

public class RunKeeperRestTemplate {

    private static final DateTimeFormatter format = DateTimeFormat.forPattern("EEE, d MMM yyyy HH:mm:ss");

    private HttpTransport transport;
    private Gson gson;

    @Value("${runkeeper.accesstoken}")
    private String accessToken;

    public RunKeeperRestTemplate() {
        this.transport = new ApacheHttpTransport();

        this.gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return format.parseLocalDateTime(json.getAsString());
            }
        }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(format.print(src));
            }
        }).create();
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
        return convertStreamToString(response.getContent());
    }

    public static String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
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
