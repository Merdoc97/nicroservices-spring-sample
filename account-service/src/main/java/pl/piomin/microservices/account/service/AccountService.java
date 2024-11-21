package pl.piomin.microservices.account.service;

import io.micrometer.tracing.annotation.NewSpan;
import org.springframework.stereotype.Component;
import pl.piomin.microservices.account.model.Account;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountService {
    @NewSpan
    public List<Account> findByCustomer(List<Account> accounts,Integer customerId){
        return accounts.stream()
                .filter(it -> it.getCustomerId().intValue() == customerId.intValue())
                .collect(Collectors.toList());
    }
}
