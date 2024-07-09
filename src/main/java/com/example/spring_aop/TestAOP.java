package com.example.spring_aop;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class TestAOP {

    @Pointcut("execution(* com.example.spring_aop.TestController.*(..))")
    private void allController(){}

    @Pointcut("execution(* com.example.spring_aop.TestController.test1(..))")
    private void test1(){}

    @Around("allController()")
    public Object logging(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        synchronized (this) {
            try (FileWriter inputWriter = new FileWriter("input.txt", true);
                 FileWriter outputWriter = new FileWriter("output.txt", true)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                // Logging input parameters
                for (Object arg : joinPoint.getArgs()) {
                    // 타임스탬프
                    inputWriter.write(dateFormat.format(new Date()) + " ");
                    // 스레드 이름
                    inputWriter.write(Thread.currentThread().getName() + " ");
                    inputWriter.write(arg.toString() + "\n");
                }

                // Proceeding with the method execution
                Object result = joinPoint.proceed();

                // Logging output parameter
                // 타임스탬프
                outputWriter.write(dateFormat.format(new Date()) + " ");
                // 스레드 이름
                outputWriter.write(Thread.currentThread().getName() + " ");
                outputWriter.write(result.toString() + "\n");

                return result;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error writing to log files", e);
            }
        }
    }
}
