package com.ragingscout.portfolio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class EnvConfig {
    // This class can be used for additional environment-specific configurations
    // spring-dotenv library enables reading .env files automatically
}

