package pl.piomin.microservices.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaServer
//@EnableAdminServer
public class EurekaDiscovery {

	public static void main(String[] args) {
		SpringApplication.run(EurekaDiscovery.class, args);
	}

}
