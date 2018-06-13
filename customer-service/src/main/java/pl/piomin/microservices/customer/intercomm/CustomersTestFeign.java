package pl.piomin.microservices.customer.intercomm;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.piomin.microservices.customer.model.Customer;

import java.util.List;

/**

 */
@FeignClient(value = "customer-service")
public interface CustomersTestFeign {

    @RequestMapping(method = RequestMethod.GET,value = "/customers")
    List<Customer>getAll();
}
