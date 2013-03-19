package org.runio.garmin;

import java.util.Arrays;
import java.util.Collections;
import org.runio.util.CookieInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:garmin.properties")
public class GarminConfig {

    @Bean
    public ClientHttpRequestInterceptor cookieInterceptor() {
        return new CookieInterceptor();
    }

    @Bean(name = "garminRestTemplate")
    public RestTemplate garminRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Arrays.asList(new HttpMessageConverter<?>[]{new GarminGsonHttpMessageConverter(), new StringHttpMessageConverter()}));
        restTemplate.setInterceptors(Collections.singletonList(cookieInterceptor()));
        return restTemplate;
    }
}
