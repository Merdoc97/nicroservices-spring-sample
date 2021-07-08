package pl.piomin.microservices.account.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.piomin.microservices.account.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CustomerService {

    private final List<Account> accounts = new ArrayList<>();

    public CustomerService() {
        accounts.add(new Account(1, 1, "111111"));
        accounts.add(new Account(2, 2, "222222"));
        accounts.add(new Account(3, 3, "333333"));
        accounts.add(new Account(4, 4, "444444"));
        accounts.add(new Account(5, 1, "555555"));
        accounts.add(new Account(6, 2, "666666"));
        accounts.add(new Account(7, 2, "777777"));
    }

    public List<Account> findByCustomer(Integer customerId) {
        log.info(String.format("Account.findByCustomer(%s)", customerId));
        return accounts.stream()
                .filter(it -> it.getCustomerId().intValue() == customerId.intValue())
                .collect(Collectors.toList());
    }
}
