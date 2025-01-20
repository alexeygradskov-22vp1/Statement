package ru.gav.creditbank.statement.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ServiceLog {


    @Pointcut("execution(public  * ru.gav.creditbank.statement.service.StatementService.*(..))")
    private void callAtDealService() {
    }

    @Before("callAtDealService()")
    private void beforeCallMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Arrays.stream(joinPoint.getArgs()).forEach(x -> log.info("Входные данные в метод {}:{}", methodName, x.toString()));
    }

    @AfterReturning(value = "callAtDealService()", returning = "returned")
    private void afterCallMethods(JoinPoint joinPoint, Object returned) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Выходные данные из метода {}:{}", methodName, returned);
    }

}

