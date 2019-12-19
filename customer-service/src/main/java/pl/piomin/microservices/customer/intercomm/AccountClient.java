package pl.piomin.microservices.customer.intercomm;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.piomin.microservices.customer.model.Account;

import java.util.List;

@FeignClient(value = "account-service", fallback = AccountFallback.class)
public interface AccountClient {

    @GetMapping(value = "/accounts/customer/{customerId}")
    List<Account> getAccounts(@PathVariable("customerId") Integer customerId);

}
