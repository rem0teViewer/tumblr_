package com.tmblr.blog.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Pointcut("@annotation(Loggable)")
    public void executeLogging() {
    }

    @Before("executeLogging()")
    public void log(JoinPoint joinPoint) {
        log.info("New user registered");
    }

}