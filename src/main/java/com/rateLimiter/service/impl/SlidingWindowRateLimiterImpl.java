package com.rateLimiter.service.impl;

import com.rateLimiter.service.RateLimiter;
import org.springframework.stereotype.Service;

@Service
public class SlidingWindowRateLimiterImpl implements RateLimiter {
    @Override
    public boolean isRequestPossible(String key) {
        return false;
    }
}
