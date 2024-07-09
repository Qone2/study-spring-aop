package com.example.spring_aop.logtracer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogTraceAspect {

    private final LogTrace logTrace;

    @Pointcut("execution(* com.example.spring_aop..*(..))")
    void allMethods() {
    }

    @Pointcut("execution(* com.example.spring_aop.logtracer..*(..))")
    void logTraceMethods() {
    }

    @Around("allMethods() && !logTraceMethods()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;

        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}