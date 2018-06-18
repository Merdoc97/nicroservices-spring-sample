package pl.piomin.microservices.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 */
@Configuration
public class SecurityActuatorConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${security.user.name}")
    private String userName;
    @Value("${management.security.roles}")
    private String userRole;
    @Value("${management.context-path}")
    private String actuatorEndpoint;
    @Value("${security.user.password}")
    private String userPassword;
    @Value("${management.context-path}")
    private String actuatorPath;
    @Value("${management.security.roles}")
    private String actuatorRole;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/**").permitAll();
    }

    //    in boot 2.0 is required
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                //    in boot 2.0 password encoder is required
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser(userName).password(userPassword).roles(userRole);
    }
}
