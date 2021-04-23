package pl.piomin.microservices.customer.config.metrics;

import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class GetHttpTimer {

    @Pointcut("@within(requestMapping)")
    public void restController(RequestMapping requestMapping) {
    }


    @Around("(restController(requestMapping))")
    public Object before(ProceedingJoinPoint joinPoint, RequestMapping requestMapping) throws Throwable {
        var request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        var timer = Metrics.timer("http_method", request.getMethod(), request.getRequestURI());

        log.info("start proceed");

        try {
            var response = joinPoint.proceed();
            timer.count();
            return response;
        } catch (Throwable throwable) {
            timer.count();
            throw throwable;
        }

    }
}
