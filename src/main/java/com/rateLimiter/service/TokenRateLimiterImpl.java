package com.rateLimiter.service;

import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

import static com.rateLimiter.constants.RateLimitConstant.MAX_AMOUNT;

@Service
public class TokenRateLimiterImpl {
    static HashMap<String, Pair<Integer, Long>> rateLimiterStore = new HashMap<>();

    public boolean checkRateLimiter(String key) {
        if (!rateLimiterStore.containsKey(key)) {
            rateLimiterStore.put(key, new Pair<>(MAX_AMOUNT, System.currentTimeMillis()));
        } else {
            Pair<Integer, Long> p = rateLimiterStore.get(key);
            Integer currentCount = p.getKey();
            Long lastUpdate = p.getValue();
            Date d1 = new Date(System.currentTimeMillis());
            Date d2 = new Date(lastUpdate);
            long diff = d2.getTime() - d1.getTime();
            long diffMinutes = diff / (60 * 1000);
            if (diffMinutes < 0) {
                rateLimiterStore.put(key, new Pair<>(MAX_AMOUNT, System.currentTimeMillis()));
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
