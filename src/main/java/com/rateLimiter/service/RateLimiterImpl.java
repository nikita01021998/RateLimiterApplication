package com.rateLimiter.service;

import com.rateLimiter.constants.RateLimitConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterImpl implements RateLimiter {

    final
    TokenRateLimiterImpl tokenRateLimiter;

    public RateLimiterImpl(TokenRateLimiterImpl tokenRateLimiter) {
        this.tokenRateLimiter = tokenRateLimiter;
    }

    @Override
    public boolean checkRateLimiter(String key, String type) {
        switch (type) {
            case RateLimitConstant.TOKEN_TYPE:
                return tokenRateLimiter.checkRateLimiter(key);
            default:
                return true;
        }
    }
}
