package com.rateLimiter.factory;

import com.rateLimiter.enums.RateLimiterEnum;
import com.rateLimiter.service.RateLimiter;
import com.rateLimiter.service.impl.LeakyBucketRateLimiterImpl;
import com.rateLimiter.service.impl.SlidingWindowRateLimiterImpl;
import com.rateLimiter.service.impl.TokenRateLimiterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterFactory {

    @Autowired
    TokenRateLimiterImpl tokenRateLimiter;

    @Autowired
    LeakyBucketRateLimiterImpl leakyBucketRateLimiter;

    @Autowired
    SlidingWindowRateLimiterImpl slidingWindowRateLimiter;


    public RateLimiter getRateLimiterInstance(String type) {
        return getRateLimiter(type, tokenRateLimiter, leakyBucketRateLimiter, slidingWindowRateLimiter);
    }

    public static RateLimiter getRateLimiter(String type, TokenRateLimiterImpl tokenRateLimiter, LeakyBucketRateLimiterImpl leakyBucketRateLimiter, SlidingWindowRateLimiterImpl slidingWindowRateLimiter) {
        switch (RateLimiterEnum.get(type)) {
            case TOKEN_RATE_LIMITER:
                return tokenRateLimiter;
            case LEAKY_BUCKET_RATE_LIMITER:
                return leakyBucketRateLimiter;
            case SLIDING_WINDOW_RATE_LIMITER:
                return slidingWindowRateLimiter;
            default:
                return null;
        }
    }
}
