package com.rateLimiter.service.impl;

import com.rateLimiter.processor.*;
import com.rateLimiter.service.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static com.rateLimiter.processor.SlidingWindowRateLimiterProcessorWithQueue.getStartTime;

public class SlidingWindowRateLimiterWithQueue implements RateLimiter {

    @Autowired
    private SlidingWindowRateLimiterProcessorWithQueue slidingWindowRateLimiterProcessorWithQueue;

    private Map<String, SlidingWindowRateLimiterProcessorWithQueue> slidingWindowRateLimiterBucket = new HashMap<>();

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
