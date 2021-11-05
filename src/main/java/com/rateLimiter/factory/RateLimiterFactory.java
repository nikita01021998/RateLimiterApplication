package com.rateLimiter.factory;

import com.rateLimiter.enums.RateLimiterEnum;
import com.rateLimiter.service.RateLimiter;
import com.rateLimiter.service.impl.LeakyBucketRateLimiterImpl;
import com.rateLimiter.service.impl.SlidingWindowRateLimiterImpl;
import com.rateLimiter.service.impl.SlidingWindowRateLimiterWithQueue;
import com.rateLimiter.service.impl.TokenRateLimiterImpl;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterFactory {

    private final TokenRateLimiterImpl tokenRateLimiter;

    final LeakyBucketRateLimiterImpl leakyBucketRateLimiter;

    final
    SlidingWindowRateLimiterImpl slidingWindowRateLimiter;

    private final SlidingWindowRateLimiterWithQueue slidingWindowRateLimiterWithQueue;

    public RateLimiterFactory(SlidingWindowRateLimiterImpl slidingWindowRateLimiter, TokenRateLimiterImpl tokenRateLimiter, LeakyBucketRateLimiterImpl leakyBucketRateLimiter, SlidingWindowRateLimiterWithQueue slidingWindowRateLimiterWithQueue) {
        this.slidingWindowRateLimiter = slidingWindowRateLimiter;
        this.tokenRateLimiter = tokenRateLimiter;
        this.leakyBucketRateLimiter = leakyBucketRateLimiter;
        this.slidingWindowRateLimiterWithQueue = slidingWindowRateLimiterWithQueue;
    }


    public RateLimiter getRateLimiterInstance(String type) {
        return getRateLimiter(type, tokenRateLimiter, leakyBucketRateLimiter, slidingWindowRateLimiter, slidingWindowRateLimiterWithQueue);
    }

    public static RateLimiter getRateLimiter(String type, TokenRateLimiterImpl tokenRateLimiter, LeakyBucketRateLimiterImpl leakyBucketRateLimiter, SlidingWindowRateLimiterImpl slidingWindowRateLimiter, SlidingWindowRateLimiterWithQueue slidingWindowRateLimiterWithQueue) {
        switch (RateLimiterEnum.get(type)) {
            case TOKEN_RATE_LIMITER:
                return tokenRateLimiter;
            case LEAKY_BUCKET_RATE_LIMITER:
                return leakyBucketRateLimiter;
            case SLIDING_WINDOW_RATE_LIMITER:
                return slidingWindowRateLimiter;
            case SLIDING_WINDOW_RATE_LIMITER_WITH_QUEUE:
                return slidingWindowRateLimiterWithQueue;
            default:
                return null;
        }
    }
}
