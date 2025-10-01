package com.example.Blogs.ScheduleJobs;


import com.example.Blogs.Utils.FixedWindowRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RateLimiterJob {
    private final FixedWindowRateLimiter fixedWindowRateLimiter;

    @Autowired
    public RateLimiterJob(FixedWindowRateLimiter fixedWindowRateLimiter){
        this.fixedWindowRateLimiter = fixedWindowRateLimiter;
    }

    @Scheduled(fixedRate = 180000)
    public void cleanRateLimiterDataStructure(){
          fixedWindowRateLimiter.cleanRateLimiterDataStructure();
    }
}
