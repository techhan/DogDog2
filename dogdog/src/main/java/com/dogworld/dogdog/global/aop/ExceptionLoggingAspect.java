package com.dogworld.dogdog.global.aop;

import com.dogworld.dogdog.global.error.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExceptionLoggingAspect {

  @Pointcut("execution(* com.dogworld.dogdog..application..*Service.*(..))")
  private void allServiceMethods() {

  }

  @AfterThrowing(pointcut = "allServiceMethods()", throwing = "exception")
  public void logServiceException(Throwable exception) {
    if(exception instanceof CustomException customException) {
      log.error("Service Layer Exception: Code = [{}], Message = [{}]",
          customException.getErrorCode().getCode(), customException.getMessage());
    } else {
      log.error("[UnhandledException] {}: {}", exception.getClass().getSimpleName()
          , exception.getMessage(), exception);
    }
  }
}
