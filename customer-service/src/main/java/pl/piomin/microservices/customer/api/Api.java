package pl.piomin.microservices.customer.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piomin.microservices.customer.exceptions.CustomerNotFoundException;
import pl.piomin.microservices.customer.intercomm.AccountClient;
import pl.piomin.microservices.customer.model.Account;
import pl.piomin.microservices.customer.model.Customer;
import pl.piomin.microservices.customer.model.CustomerType;

import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class Api {

    private final AccountClient accountClient;
    private final List<Customer> customers = List.of(
            new Customer(1, "12345", "Adam Kowalski", CustomerType.INDIVIDUAL),
            new Customer(2, "12346", "Anna Malinowska", CustomerType.INDIVIDUAL),
            new Customer(3, "12347", "Paweł Michalski", CustomerType.INDIVIDUAL),
            new Customer(4, "12348", "Karolina Lewandowska", CustomerType.INDIVIDUAL)
    );

    @GetMapping("/pesel/{pesel}")
    public Customer findByPesel(@PathVariable("pesel") String pesel) {
        log.info(String.format("Customer.findByPesel(%s)", pesel));
        return customers.stream().filter(it -> it.getPesel().equals(pesel))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("customer not found with pesel: " + pesel));
    }

    @GetMapping()
    public List<Customer> findAll() {
        log.info("Customer.findAll()");
        return customers;
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable("id") Integer id) {
        log.info(String.format("Customer.findById(%s)", id));
        var customer = customers.stream()
                .filter(it -> it.getId().intValue() == id.intValue())
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("customer not found  with id:" + id));
        List<Account> accounts = accountClient.getAccounts(id);
        customer.setAccounts(accounts);
        return customer;
    }

}
