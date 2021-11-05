package com.rateLimiter.processor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

@Service
public class SlidingWindowRateLimiterProcessorWithQueue {

    private class Pair {
        Long key;
        Integer value;

        public Pair(Long key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public Long getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }
    }
    @Value("${sliding.window.time.window.min}")
    static private int timeWindowMin;

    @Value("${sliding.window.rate.limiter.max.capacity}")
    private int capacity;

    private HashMap<String, Deque<Pair>> store = new HashMap<>();

    static public long getStartTime() {
        Long now = System.currentTimeMillis();
        Long startTime = now - timeWindowMin * 1000 * 60;
        return startTime;
    }

    public int getCurrentWindow(String key, long startTime) {
        Deque<Pair> que = store.get(key);
        int prevCount = 0;
        while(!que.isEmpty()) {
            long time = que.peek().getKey();
            int count = que.peek().getValue();
            if(time <= startTime) {
                que.poll();
            } else {
                int lastCount = que.getLast().getValue();
                return lastCount - prevCount;
            }
            prevCount = count ;
        }
        store.remove(key);
        return 0;
    }

    public boolean allow(int count) {
        return count <= capacity;
    }

    public void register(String key) {
        long now = System.currentTimeMillis();
        if(!store.containsKey(key)) {
           Deque<Pair> que = new ArrayDeque<>();
           que.add(new Pair(now, 1));
        } else {
            Deque<Pair> que = store.get(key);
            long lastTime = que.getLast().getKey();
            int lastValue = que.getLast().getValue();
            if(lastTime == now) {
                que.removeLast();
            }
            que.addLast(new Pair(now, lastValue + 1));
        }
    }
}
