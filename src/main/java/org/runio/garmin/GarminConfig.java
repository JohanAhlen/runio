package org.runio.garmin;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:garmin.properties")
public class GarminConfig {

    @Bean
    public AuthorizingHttpClientRequestFactory garminAuthorisingRequestFactory() {
        return new AuthorizingHttpClientRequestFactory();
    }

    @Bean(name = "garminRestTemplate")
    public RestTemplate garminRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(garminAuthorisingRequestFactory());
        restTemplate.setMessageConverters(Arrays.asList(new HttpMessageConverter<?>[]{new GarminGsonHttpMessageConverter(), new StringHttpMessageConverter()}));
        return restTemplate;
    }
}
