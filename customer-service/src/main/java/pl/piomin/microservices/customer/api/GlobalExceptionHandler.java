package pl.piomin.microservices.customer.api;

import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import pl.piomin.microservices.customer.exceptions.GlobalBusinessException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private Tracer tracer;

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponse(ResponseStatusException responseStatusException) {
        log.error("exception: {},reason {}", responseStatusException.getMessage(), responseStatusException.getReason(), responseStatusException);
        Map<String, String> body = new HashMap<>();
        if (responseStatusException instanceof GlobalBusinessException) {
            GlobalBusinessException e = (GlobalBusinessException) responseStatusException;
            body.put("error", e.getErrorCode());
        }
        body.put("reason", responseStatusException.getReason());
        body.putIfAbsent("error", responseStatusException.getMessage());
        tracer.currentSpan().error(responseStatusException).tag("exception", responseStatusException.getClass().getName());
        return ResponseEntity.status(responseStatusException.getStatusCode())
                .body(body);
    }
}
