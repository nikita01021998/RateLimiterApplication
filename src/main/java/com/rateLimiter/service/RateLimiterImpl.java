package com.rateLimiter.service;

import com.rateLimiter.constants.RateLimitConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterImpl implements RateLimiter {

    @Autowired
    TokenRateLimiterImpl tokenRateLimiter;

    @Override
    public boolean checkRateLimiter(String key, String type) {
        switch (type) {
            case RateLimitConstant.tokenType:
                return tokenRateLimiter.checkRateLimiter(key);
            default:
                return true;
        }
    }
}
