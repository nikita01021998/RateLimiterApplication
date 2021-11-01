package com.rateLimiter.service.impl;

import com.rateLimiter.processor.LeakyBucketRateLimiterProcessor;
import com.rateLimiter.service.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeakyBucketRateLimiterImpl implements RateLimiter {
    @Value("${leaky.rate.limiter.max.bucket.size}")
    private int rateLimiterBucketSize;

    private final LeakyBucketRateLimiterProcessor leakyBucketRateLimiterProcessor;
    private Map<String, LeakyBucketRateLimiterProcessor> rateLimiterBucket = new HashMap<>();

    public LeakyBucketRateLimiterImpl(LeakyBucketRateLimiterProcessor leakyBucketRateLimiterProcessor) {
        this.leakyBucketRateLimiterProcessor = leakyBucketRateLimiterProcessor;
    }

    @Override
    public boolean isRequestPossible(String key) {
        if (!rateLimiterBucket.containsKey(key)) {
            leakyBucketRateLimiterProcessor.setLeakyBucket(new ArrayList<>(Arrays.asList(key)));
            rateLimiterBucket.put(key, leakyBucketRateLimiterProcessor);
        } else {
            LeakyBucketRateLimiterProcessor leakyBucketRateLimiterProcessor = rateLimiterBucket.get(key);
            List<String> leakyBucket = leakyBucketRateLimiterProcessor.getLeakyBucket();
            if (leakyBucket.size() < rateLimiterBucketSize) {
                leakyBucket.add(key);
                leakyBucketRateLimiterProcessor.setLeakyBucket(leakyBucket);
                rateLimiterBucket.put(key, leakyBucketRateLimiterProcessor);
            } else {
                return false;
            }
        }
        return true;
    }
}
