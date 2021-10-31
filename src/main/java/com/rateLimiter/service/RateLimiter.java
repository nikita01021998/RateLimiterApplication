package com.rateLimiter.service;

import org.springframework.stereotype.Service;

@Service
public interface RateLimiter {
    boolean isRequestPossible(String key);
}
