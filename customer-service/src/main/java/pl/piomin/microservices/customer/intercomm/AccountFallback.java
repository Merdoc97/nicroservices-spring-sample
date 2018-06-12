package pl.piomin.microservices.customer.intercomm;

import org.springframework.stereotype.Component;
import pl.piomin.microservices.customer.model.Account;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountFallback implements AccountClient {

	@Override
	public List<Account> getAccounts(Integer customerId) {
		List<Account> acc = new ArrayList<>();
		return acc;
	}
	
}
