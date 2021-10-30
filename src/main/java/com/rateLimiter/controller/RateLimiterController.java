package com.rateLimiter.controller;

import com.rateLimiter.service.RateLimiter;
import com.swiggy.alchemist.pojo.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RateLimiterController {

    final
    RateLimiter rateLimiter;

    public RateLimiterController(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @RequestMapping(
            value = "/rateLimiter/{key}/{type}",
            method = RequestMethod.GET
    )
    @ResponseBody
    public BaseResponse checkRateLimit(@PathVariable(value = "key") String key, @PathVariable(value = "type") String type) {
        try {
            boolean result = rateLimiter.checkRateLimiter(key, type);
            if (result) {
                return new BaseResponse(0, "Success");
            }
            return new BaseResponse(0, "Rate limit accessed");
        } catch (Exception e) {
            return new BaseResponse(1, "Something went wrong.");
        }
    }
}
