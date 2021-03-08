package pl.piomin.microservices.customer.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Getter
public class CustomerNotFoundException extends ResponseStatusException implements GlobalBusinessException {

    private static final long serialVersionUID = 1L;
    private final String errorCode;

    public CustomerNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
        this.errorCode = "404";
    }
}
