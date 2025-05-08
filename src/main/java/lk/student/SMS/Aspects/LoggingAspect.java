package lk.student.SMS.Aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("execution(* lk.student.SMS.Service.*.*(..)) || execution(* lk.student.SMS.Controller.*.*(..))")
    public void applicationPackagePointcut() {
        // Match all methods in Service and Controller packages
    }

    @Before("applicationPackagePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info(" Entering method: " + joinPoint.getSignature().toShortString() +
                    " with arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "applicationPackagePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("✅ Method returned: " + joinPoint.getSignature().toShortString() +
                    " with result: " + result);
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.severe(" Exception in: " + joinPoint.getSignature().toShortString() +
                      " with message: " + ex.getMessage());
    }
}
