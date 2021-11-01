package com.rateLimiter.service.impl;

import com.rateLimiter.processor.SlidingWindowRateLimiterProcessor;
import com.rateLimiter.service.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SlidingWindowRateLimiterImpl implements RateLimiter {
    @Value("${sliding.window.time.window.min}")
    private int timeWindowMin;
    @Value("${sliding.window.rate.limiter.max.capacity}")
    private int capacity;
    private final SlidingWindowRateLimiterProcessor slidingWindowRateLimiterProcessor;

    public SlidingWindowRateLimiterImpl(SlidingWindowRateLimiterProcessor slidingWindowRateLimiterProcessor) {
        this.slidingWindowRateLimiterProcessor = slidingWindowRateLimiterProcessor;
    }

    @Override
    public boolean isRequestPossible(String key) {
        Long now = System.currentTimeMillis();
        Long startTime = now - timeWindowMin * 1000 * 60;
        int window = slidingWindowRateLimiterProcessor.getCurrentWindow(key, startTime);
        if (window > capacity) {
            return false;
        }
        slidingWindowRateLimiterProcessor.registerRequest(key, now);
        return true;
    }
}
