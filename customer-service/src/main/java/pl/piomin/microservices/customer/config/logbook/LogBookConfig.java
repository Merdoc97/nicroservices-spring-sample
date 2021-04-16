package pl.piomin.microservices.customer.config.logbook;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.zalando.logbook.HttpLogWriter;

@Configuration
public class LogBookConfig {

    @Bean
    @Profile("jsonLogs")
    HttpLogWriter httpLogWriter(){
        return new MdcJsonLogWriter();
    }
}
