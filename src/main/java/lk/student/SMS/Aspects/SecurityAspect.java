package lk.student.SMS.Aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class SecurityAspect {

    private final Logger logger = Logger.getLogger(SecurityAspect.class.getName());

    // Intercept login method
    @Pointcut("execution(* lk.student.SMS.Controller.AuthController.login(..))")
    public void loginPointcut() {}

    // Intercept signup/registration method
    @Pointcut("execution(* lk.student.SMS.Controller.AuthController(..))")
    public void registerPointcut() {}

    // Any secured method: e.g., controller/service that require user authentication
    @Pointcut("execution(* lk.student.SMS.Service.*.*(..)) && !within(lk.student.SMS.Service.impl.AuthServiceImpl)")
    public void securedServices() {}

    @Before("loginPointcut()")
    public void logLoginAttempt(JoinPoint joinPoint) {
        logger.info(" Login attempt with args: " + joinPoint.getArgs()[0]);
    }

    @Before("registerPointcut()")
    public void logRegistration(JoinPoint joinPoint) {
        logger.info(" Registration attempt: " + joinPoint.getArgs()[0]);
    }

    @Before("securedServices()")
    public void logAccessToSecuredService(JoinPoint joinPoint) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info(" User '" + username + "' accessed: " + joinPoint.getSignature().toShortString());
    }
}
