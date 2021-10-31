package com.rateLimiter.service.impl;

import com.rateLimiter.service.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlidingWindowRateLimiterImpl implements RateLimiter {
    @Override
    public boolean isRequestPossible(String key) {

        return false;
    }
}
