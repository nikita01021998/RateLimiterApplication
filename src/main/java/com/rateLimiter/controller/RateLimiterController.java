package com.rateLimiter.controller;

import com.rateLimiter.factory.RateLimiterFactory;
import com.rateLimiter.pojo.BaseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RateLimiterController {


    public RateLimiterController(RateLimiterFactory rateLimiterFactory) {
        this.rateLimiterFactory = rateLimiterFactory;
    }

    final
    RateLimiterFactory rateLimiterFactory;

    @Value("${rate.limiter.type}")
    String type;

    @RequestMapping(
            value = "/requestPossible/{key}",
            method = RequestMethod.GET
    )
    @ResponseBody
    public BaseResponse isRequestPossible(@PathVariable(value = "key") String key) {
        try {
            boolean result = rateLimiterFactory.getRateLimiterInstance(type).isRequestPossible(key);
            if (result) {
                return new BaseResponse(0, "Success");
            }
            return new BaseResponse(0, "Rate limit exceeded");
        } catch (Exception e) {
            return new BaseResponse(1, "Something went wrong.");
        }
    }
}
