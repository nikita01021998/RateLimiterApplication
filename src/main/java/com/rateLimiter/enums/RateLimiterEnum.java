package com.rateLimiter.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum RateLimiterEnum {
    UNKONWN(0,"unknown"),
    TOKEN_RATE_LIMITER(1, "token"),
    LEAKY_BUCKET_RATE_LIMITER(2, "leaky"),
    SLIDING_WINDOW_RATE_LIMITER(3, "slidingWindow");

    final private int value;
    final private String name;

    public  int getValue() {
        return value;
    }

    public String getName(){
        return name;
    }

    RateLimiterEnum(int value,String name){
        this.value=value;
        this.name=name;
    }

    public static RateLimiterEnum get(String key) {
        return lookup.containsKey(key)? (RateLimiterEnum) lookup.get(key) :UNKONWN;
    }

    public static RateLimiterEnum get(long value) {
        return valueLookup.containsKey(value)? (RateLimiterEnum) valueLookup.get(value) :UNKONWN;
    }

    private static Map lookup = new HashMap<Long,RateLimiterEnum>();
    private static Map valueLookup = new HashMap<Long,RateLimiterEnum>();

    static {
        lookup = (Map) EnumSet.allOf(RateLimiterEnum.class).stream().collect(Collectors.toMap((x) -> x.name, (x) -> x));
        valueLookup = (Map) EnumSet.allOf(RateLimiterEnum.class).stream().collect(Collectors.toMap((x) -> x.value, (x) -> x));
    }
}
