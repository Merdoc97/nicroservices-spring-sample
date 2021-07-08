package pl.piomin.microservices.account.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piomin.microservices.account.model.Account;
import pl.piomin.microservices.account.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@Slf4j
public class Api {
    private final List<Account> accounts = new ArrayList<>();
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer/{customer}")
    public List<Account> findByCustomer(@PathVariable("customer") Integer customerId) {
        log.info(String.format("Account.findByCustomer(%s)", customerId));
        return customerService.findByCustomer(customerId);
    }

    @GetMapping("/")
    public List<Account> findAll() {
        log.info("Account.findAll()");
        return accounts;
    }

}
