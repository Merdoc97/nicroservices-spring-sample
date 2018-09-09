package pl.piomin.microservices.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDasboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixDasboardApplication.class, args);
	}
	
}
