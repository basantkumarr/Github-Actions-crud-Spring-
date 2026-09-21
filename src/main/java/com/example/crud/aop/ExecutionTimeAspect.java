package com.example.crud.aop;

import com.example.crud.annotation.ExecutionTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class ExecutionTimeAspect {

    @Around("@annotation(executionTime)")
    public Object calculateExecutionTime(
            ProceedingJoinPoint joinPoint,
            ExecutionTime executionTime) throws Throwable {

        long startTime = System.currentTimeMillis();

        System.out.println("Before method: "
                + joinPoint.getSignature().getName());

        System.out.println("Annotation value: "
                + executionTime.value());

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        System.out.println(
                "Method "
                        + joinPoint.getSignature().getName()
                        + " took "
                        + (endTime - startTime)
                        + " ms"
        );

        System.out.println("After method");

        return result;
    }
}