package com.example.crud.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StudentAspect {


    // ============================================================
    // 1. @Before
    // ============================================================
    // This method runs BEFORE the actual StudentService method.
    //
    // Example:
    //
    // Controller
    //     ↓
    // @Before
    //     ↓
    // getStudentServ()
    //
    // It is useful for:
    // - Logging
    // - Checking something before method execution
    // - Authentication/authorization checks
    // ============================================================

    @Before("execution(* com.example.crud.service..*(..))")
    public void beforeMethod() {

        System.out.println("========== @Before ==========");
        System.out.println("Before StudentService method");
    }


    // ============================================================
    // 2. @After
    // ============================================================
    // This method runs AFTER the target method finishes.
    //
    // IMPORTANT:
    // It runs whether the method:
    // - succeeds
    // - throws an exception
    //
    // Similar to finally{} in Java.
    //
    // Example:
    //
    // getStudentServ()
    //       ↓
    // method finishes
    //       ↓
    // @After
    // ============================================================

    @After("execution(* com.example.crud.service..*(..))")
    public void afterMethod() {

        System.out.println("========== @After ==========");
        System.out.println("StudentService method finished");
    }


    // ============================================================
    // 3. @AfterReturning
    // ============================================================
    // This runs ONLY when the method successfully returns.
    //
    // If method throws an exception:
    // @AfterReturning will NOT execute.
    //
    // 'result' contains the value returned by the original method.
    //
    // Example:
    //
    // getStudentServ()
    //       ↓
    // StudentRespDTO returned
    //       ↓
    // @AfterReturning
    //
    // ============================================================

    @AfterReturning(
            pointcut = "execution(* com.example.crud.service..*(..))",
            returning = "result"
    )
    public void afterReturningMethod(Object result) {

        System.out.println("========== @AfterReturning ==========");
        System.out.println("Method executed successfully");
        System.out.println("Returned result: " + result);
    }


    // ============================================================
    // 4. @AfterThrowing
    // ============================================================
    // This runs ONLY when the target method throws an exception.
    //
    // In your project, for example:
    //
    // getStudentServ(999L)
    //       ↓
    // Student not found
    //       ↓
    // ResourceNotFoundException
    //       ↓
    // @AfterThrowing
    //
    // 'ex' contains the exception.
    //
    // ============================================================

    @AfterThrowing(
            pointcut = "execution(* com.example.crud.service..*(..))",
            throwing = "ex"
    )
    public void afterThrowingMethod(Exception ex) {

        System.out.println("========== @AfterThrowing ==========");
        System.out.println("Exception occurred!");
        System.out.println("Exception: " + ex.getMessage());
    }


    // ============================================================
    // 5. @Around
    // ============================================================
    // @Around gives us control BEFORE and AFTER the method.
    //
    // It is the most powerful advice.
    //
    // The important line is:
    //
    //     joinPoint.proceed();
    //
    // This actually executes the original StudentService method.
    //
    // Flow:
    //
    // @Around Before
    //       ↓
    // joinPoint.proceed()
    //       ↓
    // StudentService method
    //       ↓
    // @Around After
    //
    // We can also:
    // - measure execution time
    // - modify arguments
    // - modify return value
    // - stop method execution
    // - handle exceptions
    //
    // ============================================================

    @Around("execution(* com.example.crud.service..*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint)
            throws Throwable {

        System.out.println("========== @Around ==========");
        System.out.println("Around - BEFORE method");

        // This executes the ORIGINAL StudentService method
        Object result = joinPoint.proceed();

        System.out.println("Around - AFTER method");

        // Return original result back to the controller
        return result;
    }
}