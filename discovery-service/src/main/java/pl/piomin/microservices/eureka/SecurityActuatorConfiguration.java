package pl.piomin.microservices.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 */
@Configuration
@Order(2)
@EnableWebSecurity
public class SecurityActuatorConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${security.user.name}")
    private String userName;
    @Value("${management.security.roles}")
    private String userRole;
    @Value("${security.user.password}")
    private String userPassword;
    @Value("${management.context-path}")
    private String actuatorPath;
    @Value("${management.security.roles}")
    private String actuatorRole;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .anonymous().disable()
                .requestMatcher(request -> {
//                    if present some other authentication type add to request header or param or something else
                    String requestUri=request.getRequestURI().toLowerCase();
                    return requestUri.toLowerCase().contains(actuatorPath);
                })
                .authorizeRequests()
                .antMatchers(actuatorPath + "/**").hasAnyRole(actuatorRole)
                .antMatchers("/**").denyAll()
                .and()
                .httpBasic().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser(userName).password(userPassword).roles(userRole);
    }
}
