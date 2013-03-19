package org.runio.runkeeper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:runkeeper.properties")
public class RunKeeperConfig {

    @Bean
    public ClientHttpRequestInterceptor runKeeperAuthorizationInterceptor() {
        return new RunKeeperAuthorizationInterceptor();
    }

    public ClientHttpRequestInterceptor runKeeperContentTypeInterceptor() {
        return new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                if (request.getURI().getPath().startsWith("/fitnessActivities")) {
                    request.getHeaders().set("Content-Type", "application/vnd.com.runkeeper.NewFitnessActivity+json");
                }
                return execution.execute(request, body);
            }
        };
    }

    @Bean
    public RunKeeperApiClient runKeeperApiClient() {
        return new RunKeeperApiClient();
    }

    @Bean
    public Gson runKeeperGson() {
        final DateTimeFormatter runKeeperDateFormat = DateTimeFormat.forPattern("EEE, d MMM yyyy HH:mm:ss");
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return runKeeperDateFormat.parseLocalDateTime(json.getAsString());
            }
        }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(runKeeperDateFormat.print(src));
            }
        }).create();
    }

    @Bean
    RunKeeperGsonHttpMessageConverter runKeeperGsonHttpMessageConverter() {
        return new RunKeeperGsonHttpMessageConverter();
    }

    @Bean(name="runKeeperRestTemplate")
    public RestTemplate runKeeperRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Arrays.asList(new HttpMessageConverter<?>[]{runKeeperGsonHttpMessageConverter(), new StringHttpMessageConverter()}));
        restTemplate.setInterceptors(Arrays.asList(new ClientHttpRequestInterceptor[]{ runKeeperAuthorizationInterceptor(), runKeeperContentTypeInterceptor() }));
        return restTemplate;
    }
}
