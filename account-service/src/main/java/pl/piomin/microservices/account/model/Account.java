package pl.piomin.microservices.account.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {

	private Integer id;
	private Integer customerId;
	private String number;

	public Account(Integer id, Integer customerId, String number) {
		this.id = id;
		this.customerId = customerId;
		this.number = number;
	}

}
