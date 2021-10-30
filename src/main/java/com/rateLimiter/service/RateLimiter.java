package com.rateLimiter.service;

import org.springframework.stereotype.Service;

@Service
public interface RateLimiter {
       public boolean checkRateLimiter(String key, String type);
}
