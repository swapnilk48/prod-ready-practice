package com.practice.prod_features.config;

import com.practice.prod_features.auth.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AppConfig {

    @Bean
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    @Bean
    AuditorAware<String> getauditorAwareImpl(){
        return new AuditorAwareImpl();
    }
}
