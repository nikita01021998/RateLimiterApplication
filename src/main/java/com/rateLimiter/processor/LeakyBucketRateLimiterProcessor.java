package com.rateLimiter.processor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeakyBucketRateLimiterProcessor {

    public List<String> getLeakyBucket() {
        return leakyBucket;
    }

    public void setLeakyBucket(List<String> leakyBucket) {
        this.leakyBucket = leakyBucket;
    }

    private List<String> leakyBucket = new ArrayList<>();

    @Scheduled(cron = "*/10 * * * * *")
    public void processor() {
        if (!leakyBucket.isEmpty()) {
            leakyBucket.remove(0);
        }
    }

}
