package com.practice.prod_features.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
//        get sec context + get auth + get principle + get username
        return Optional.of("Swapnil");
    }
}
