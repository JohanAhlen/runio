package org.runsync.garmin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.runsync.CookieInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:garmin.properties")
public class GarminConfig {

    private static final String signInUrl = "https://connect.garmin.com/signin?login=login&login:loginUsernameField=%s&login:password=%s&login:signInButton=Sign%%20In&javax.faces.ViewState=j_id2";

    @Value("${garmin.password}")
    private String password;
    @Value("${garmin.username}")
    private String username;

    @Bean
    public ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
        return new CookieInterceptor();
    }

    @Lazy
    @Bean(name = "garminRestTemplate")
    public RestTemplate garminRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<HttpMessageConverter<?>>();
        httpMessageConverters.add(new GarminGsonHttpMessageConverter());
        httpMessageConverters.add(new StringHttpMessageConverter());
        restTemplate.setMessageConverters(httpMessageConverters);
        restTemplate.setInterceptors(Collections.singletonList(clientHttpRequestInterceptor()));
        signInTwice(restTemplate);
        return restTemplate;
    }

    private void signInTwice(RestTemplate restTemplate) {
        restTemplate.postForEntity(String.format(signInUrl, username, password), null, String.class);
        restTemplate.postForEntity(String.format(signInUrl, username, password), null, String.class);
    }
}
