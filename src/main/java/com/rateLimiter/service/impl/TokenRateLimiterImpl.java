package com.rateLimiter.service.impl;

import com.rateLimiter.service.RateLimiter;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class TokenRateLimiterImpl implements RateLimiter {

    @Value("${token.rate.limiter.max.bucket.size}")
    private int rateLimiterBucketSize;

    HashMap<String, Pair<Integer, Long>> rateLimiterStore = new HashMap<>();

    @Override
    public boolean isRequestPossible(String key) {
        if (!rateLimiterStore.containsKey(key)) {
            rateLimiterStore.put(key, new Pair<>(rateLimiterBucketSize, System.currentTimeMillis()));
        } else {
            Pair<Integer, Long> p = rateLimiterStore.get(key);
            Integer currentCount = p.getKey();
            Long lastUpdate = p.getValue();
            Date now = new Date(System.currentTimeMillis());
            Date lastUpdateDate = new Date(lastUpdate);
            long diff = lastUpdateDate.getTime() - now.getTime();
            long diffMinutes = diff / (60 * 1000);
            if (diffMinutes < 0) {
                rateLimiterStore.put(key, new Pair<>(rateLimiterBucketSize, System.currentTimeMillis()));
            } else {
                if (currentCount < 1) {
                    return false;
                } else {
                    rateLimiterStore.put(key, new Pair<>(currentCount - 1, lastUpdate));
                }
            }
        }
        return true;
    }
}
