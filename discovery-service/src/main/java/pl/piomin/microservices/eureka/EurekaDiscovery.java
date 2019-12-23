package pl.piomin.microservices.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaDiscovery {

	public static void main(String[] args) {
		SpringApplicationBuilder builder=new SpringApplicationBuilder(EurekaDiscovery.class);
		builder.addCommandLineProperties(true);
		builder.main(EurekaDiscovery.class);
		builder.run(args);

//		SpringApplication.run(EurekaDiscovery.class, args);
	}

}
