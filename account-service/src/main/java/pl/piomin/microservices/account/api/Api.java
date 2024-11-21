package pl.piomin.microservices.account.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piomin.microservices.account.exceptions.AccountNotFoundException;
import pl.piomin.microservices.account.model.Account;
import pl.piomin.microservices.account.service.AccountService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class Api {
    private final List<Account> accounts = new ArrayList<>();
    @Autowired
    private AccountService accountService;
    public Api() {
        accounts.add(new Account(1, 1, "111111"));
        accounts.add(new Account(2, 2, "222222"));
        accounts.add(new Account(3, 3, "333333"));
        accounts.add(new Account(4, 4, "444444"));
        accounts.add(new Account(5, 1, "555555"));
        accounts.add(new Account(6, 2, "666666"));
        accounts.add(new Account(7, 2, "777777"));
    }

    @GetMapping("/{number}")
    public Account findByNumber(@PathVariable("number") String number) {
        log.info(String.format("Account.findByNumber(%s)", number));
        return accounts.stream()
                .filter(it -> it.getNumber().equals(number))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException(number));
    }

    @GetMapping("/customer/{customer}")
    public List<Account> findByCustomer(@PathVariable("customer") Integer customerId) {
        log.info(String.format("Account.findByCustomer(%s)", customerId));
        return accountService.findByCustomer(accounts,customerId);
    }

    @GetMapping("/")
    public List<Account> findAll() {
        log.info("Account.findAll()");
        return accounts;
    }

}
