package com.example.Blogs.Utils;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class FixedWindowRateLimiter {
    @Value("${fixedRateLimiter.limit}")
    private  int limit;
    @Value("${fixedRateLimiter.windowSize}")
    private  long windowSize;
    private static  ConcurrentHashMap<Long, ConcurrentHashMap<Long, Integer>> windowCountsPerUser  = new ConcurrentHashMap<>();

    public FixedWindowRateLimiter() {

    }

    public  boolean allowRequest(){
        AdvancedEmailPasswordToken advancedEmailPasswordToken =(AdvancedEmailPasswordToken) SecurityContextHolder
                                                                                            .getContext()
                                                                                            .getAuthentication();
        Long userId = advancedEmailPasswordToken.getCurrentUserId();
        ConcurrentHashMap<Long, Integer> userWindows = windowCountsPerUser.computeIfAbsent(
                userId,
                k -> new ConcurrentHashMap<>()
        );
        Long currentWindow = System.currentTimeMillis() / windowSize;
        Integer newCount = userWindows.compute(currentWindow, (k, oldCount) -> {
            return (oldCount == null ? 0 : oldCount) + 1;
        });


        return newCount <= limit;

    }

    public void cleanRateLimiterDataStructure() {
        long currentTime = System.currentTimeMillis();
        long currentWindow = currentTime / windowSize;

        windowCountsPerUser.entrySet().removeIf(entry -> {
            ConcurrentHashMap<Long, Integer> userWindows = entry.getValue();
            userWindows.entrySet().removeIf(windowEntry ->
                    windowEntry.getKey() < currentWindow - 1
            );
            return userWindows.isEmpty();
        });
    }
}
