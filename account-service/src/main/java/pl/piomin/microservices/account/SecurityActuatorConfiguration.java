package pl.piomin.microservices.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import java.util.Optional;

/**
 *
 */
@Configuration
public class SecurityActuatorConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.user.name}")
    private String userName;
    @Value("${spring.security.user.password}")
    private String userPassword;

    private final String actuatorPath = "/actuator";
    @Value("${spring.security.user.roles}")
    private String actuatorRole;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .anonymous().disable()
                //filtering to basic authorization allowed only for actuator
                .requestMatcher(request -> {
                    boolean isActuator = request.getRequestURI().contains(actuatorPath);
                    String auth = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)).orElse("");
                    return (isActuator && !auth.toLowerCase().contains("bearer"));
                })
                .authorizeRequests()
                .antMatchers(actuatorPath + "/**").hasAnyRole(actuatorRole)
                //endpoint for refresh context spring cloud
                .antMatchers("/instances/**").hasAnyRole(actuatorRole)
                .antMatchers(actuatorPath + "/health").permitAll()
                .antMatchers("/**").denyAll()
                .and()
                .httpBasic().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    //    in boot 2.0 is required
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                //    in boot 2.0 password encoder is required
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser(userName).password(userPassword).roles(actuatorRole);
    }
}
