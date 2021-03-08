package pl.piomin.microservices.customer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Customer {

    private Integer id;
    private String pesel;
    private String name;
    private CustomerType type;
    private List<Account> accounts;

    public Customer(Integer id, String pesel, String name, CustomerType type) {
        this.id = id;
        this.pesel = pesel;
        this.name = name;
        this.type = type;
    }
}
