package pl.piomin.microservices.account;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfiguration {

    @Bean
    public TopicExchange hystrix(){
        return new TopicExchange("hystrixStreamOutput");
    }

    @Bean
    public TopicExchange turbine(){
        return new TopicExchange("turbineStreamInput");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(turbine())
                .to(hystrix())
                .with("#");
    }
}
