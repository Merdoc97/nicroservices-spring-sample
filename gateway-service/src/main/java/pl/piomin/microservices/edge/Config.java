package pl.piomin.microservices.edge;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


@Configuration
public class Config {


    @Bean
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(Jackson2ObjectMapperBuilder objectMapperBuilder) {

        return new MappingJackson2HttpMessageConverter(objectMapperBuilder.build());
    }

    @Bean
    Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.modules(new JavaTimeModule());
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        builder.failOnUnknownProperties(false);
        builder.featuresToEnable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);

        return builder;
    }

    @Bean
    ObjectMapper objectMapper() {
        return objectMapperBuilder().build();
    }
}
