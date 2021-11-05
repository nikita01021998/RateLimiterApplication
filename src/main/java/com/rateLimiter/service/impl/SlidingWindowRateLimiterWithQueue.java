package com.rateLimiter.service.impl;

import com.rateLimiter.processor.*;
import com.rateLimiter.service.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.rateLimiter.processor.SlidingWindowRateLimiterProcessorWithQueue.getStartTime;

@Service
public class SlidingWindowRateLimiterWithQueue implements RateLimiter {

    private final SlidingWindowRateLimiterProcessorWithQueue slidingWindowRateLimiterProcessorWithQueue;

    private Map<String, SlidingWindowRateLimiterProcessorWithQueue> slidingWindowRateLimiterBucket = new HashMap<>();

    public SlidingWindowRateLimiterWithQueue(SlidingWindowRateLimiterProcessorWithQueue slidingWindowRateLimiterProcessorWithQueue) {
        this.slidingWindowRateLimiterProcessorWithQueue = slidingWindowRateLimiterProcessorWithQueue;
    }

    @Override
    public boolean isRequestPossible(String key) {
        long startTime = getStartTime();
        if (slidingWindowRateLimiterBucket.containsKey(key)) {
            SlidingWindowRateLimiterProcessorWithQueue slidingWindowRateLimiterProcessorWithQueue = slidingWindowRateLimiterBucket.get(key);
            int count = slidingWindowRateLimiterProcessorWithQueue.getCurrentWindow(key, startTime);
            if (!slidingWindowRateLimiterProcessorWithQueue.allow(count)) {
                return false;
            }
            slidingWindowRateLimiterProcessorWithQueue.register(key);
        } else {
            slidingWindowRateLimiterProcessorWithQueue.register(key);
            slidingWindowRateLimiterBucket.put(key, slidingWindowRateLimiterProcessorWithQueue);
        }
        return true;
    }
}
