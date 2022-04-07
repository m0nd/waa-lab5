package waa.labs.lab5.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import waa.labs.lab5.entities.Logger;
import waa.labs.lab5.repositories.ILoggerRepo;

import java.time.LocalDate;
import java.time.LocalTime;

@Aspect
@Component
public class LoggerAspect {
    ILoggerRepo loggerRepo;

    @Autowired
    public LoggerAspect(ILoggerRepo loggerRepo) {
        this.loggerRepo = loggerRepo;
    }

    @Pointcut("execution(* waa.labs.lab5.controllers.*.*(..))")
    public void operationsPointCut() {}

    @After("operationsPointCut()")
    public void logOperation(JoinPoint joinPoint) {
        LocalDate curDate = LocalDate.now();
        LocalTime curTime = LocalTime.now();
        this.saveLog(curDate, curTime, Logger.PRINCIPLE_NAME, joinPoint.getSignature().getName());
        System.out.println("Logging an Operation...");
        System.out.printf("Date: %s\nTime: %s\nPrinciple: %s\nOperation: %s\n\n",
                LocalDate.now(), LocalTime.now(), Logger.PRINCIPLE_NAME, joinPoint.getSignature().getName());
    }

    public void saveLog(LocalDate curDate, LocalTime curTime, String principle, String op) {
        Logger logger = new Logger();
        logger.setDate(curDate);
        logger.setTime(curTime);
        logger.setPrinciple(principle);
        logger.setOperation(op);
        loggerRepo.save(logger);
    }
}
