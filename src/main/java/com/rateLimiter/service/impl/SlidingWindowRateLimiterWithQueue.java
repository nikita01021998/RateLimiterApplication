package com.rateLimiter.service.impl;

import com.rateLimiter.processor.*;
import com.rateLimiter.service.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SlidingWindowRateLimiterWithQueue implements RateLimiter {

    private final SlidingWindowRateLimiterProcessorWithQueue slidingWindowRateLimiterProcessorWithQueue;

    private Map<String, SlidingWindowRateLimiterProcessorWithQueue> slidingWindowRateLimiterBucket = new HashMap<>();

    public SlidingWindowRateLimiterWithQueue(SlidingWindowRateLimiterProcessorWithQueue slidingWindowRateLimiterProcessorWithQueue) {
        this.slidingWindowRateLimiterProcessorWithQueue = slidingWindowRateLimiterProcessorWithQueue;
    }

    @Override
    public boolean isRequestPossible(String key) {
        if (slidingWindowRateLimiterBucket.containsKey(key)) {
            SlidingWindowRateLimiterProcessorWithQueue slidingWindowRateLimiterProcessorWithQueue = slidingWindowRateLimiterBucket.get(key);
            return slidingWindowRateLimiterProcessorWithQueue.isRequestPossible(key);
        } else {
            slidingWindowRateLimiterBucket.put(key, slidingWindowRateLimiterProcessorWithQueue);
            return slidingWindowRateLimiterProcessorWithQueue.isRequestPossible(key);
        }
    }
}
