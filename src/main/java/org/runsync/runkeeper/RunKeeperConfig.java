package org.runsync.runkeeper;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.ClientHttpRequestInterceptor;
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

    @Bean(name="runKeeperRestTemplate")
    public RestTemplate runKeeperRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Arrays.asList(new HttpMessageConverter<?>[]{new RunKeeperGsonHttpMessageConverter(), new StringHttpMessageConverter()}));
        restTemplate.setInterceptors(Collections.singletonList(runKeeperAuthorizationInterceptor()));
        return restTemplate;
    }
}
