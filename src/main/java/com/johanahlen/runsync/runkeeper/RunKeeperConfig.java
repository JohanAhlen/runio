package com.johanahlen.runsync.runkeeper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:runkeeper.properties")
public class RunKeeperConfig {

    @Bean
    public RunKeeperRestTemplate oauthRestTemplate() {
        return new RunKeeperRestTemplate();
    }

}
