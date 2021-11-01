package com.rateLimiter.processor;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class SlidingWindowRateLimiterProcessor {
    private Map<String, HashMap<Long, Integer>> windowStore;

    public int getCurrentWindow(String key, Long startTime) {
        if (!windowStore.containsKey(key)) {
            return 0;
        }
        int totalRequest = 0;
        HashMap<Long, Integer> requestStore = windowStore.get(key);
        Iterator hmIterator = requestStore.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
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
        HashMap<Long, Integer> hm =
                new HashMap<Long, Integer>();
        if (!windowStore.containsKey(key)) {
            hm.put(time, 1);
            windowStore.put(key, hm);
        } else {
            Integer count = hm.get(time);
            hm.put(time, count + 1);
            windowStore.put(key, hm);
        }
    }
}