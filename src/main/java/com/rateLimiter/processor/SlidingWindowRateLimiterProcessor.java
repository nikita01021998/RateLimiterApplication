package com.rateLimiter.processor;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class SlidingWindowRateLimiterProcessor {
    private Map<String, TreeMap<Long, Integer>> windowStore = new TreeMap<>();

    public int getCurrentWindow(String key, Long startTime) {
        if (!windowStore.containsKey(key)) {
            return 0;
        }
        int totalRequest = 0;
        TreeMap<Long, Integer> requestStore = windowStore.get(key);
        for (Map.Entry<Long, Integer> longIntegerEntry : requestStore.entrySet()) {
            Map.Entry mapElement = longIntegerEntry;
            Long timestamp = (Long) mapElement.getValue();
            Integer count = (Integer) mapElement.getKey();
            if (timestamp > startTime) {
                totalRequest += count;
            } else {
                requestStore.remove(timestamp);
            }
        }
        if (requestStore.isEmpty()) {
            windowStore.remove(key);
        } else {
            windowStore.put(key, requestStore);
        }
        return totalRequest;
    }

    public void registerRequest(String key, Long time) {
        TreeMap<Long, Integer> hm = new TreeMap<>();
        if (!windowStore.containsKey(key)) {
            hm.put(time, 1);
        } else {
            hm = windowStore.get(key);
            Integer count = hm.get(time);
            hm.put(time, count + 1);
        }
        windowStore.put(key, hm);
    }
}
