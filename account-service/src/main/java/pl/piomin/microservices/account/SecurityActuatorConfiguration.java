package pl.piomin.microservices.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

/**
 *
 */
@Configuration
public class SecurityActuatorConfiguration {

    @Value("${spring.security.user.name}")
    private String userName;
    @Value("${spring.security.user.password}")
    private String userPassword;

    private final String actuatorPath = "/actuator";
    @Value("${spring.security.user.roles}")
    private String actuatorRole;

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.securityMatcher(actuatorPath+"/**")

                //filtering to basic authorization allowed only for actuator
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(actuatorPath + "/health").permitAll()
                        .requestMatchers(actuatorPath + "/prometheus").permitAll()
                        .requestMatchers(actuatorPath + "/**").hasAnyRole(actuatorRole)
                        .requestMatchers("/instances/**").hasAnyRole(actuatorRole)
                        .anyRequest().denyAll()
                )
                .httpBasic()
                .authenticationEntryPoint(entryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    BasicAuthenticationEntryPoint entryPoint(){
        final var entryPoint=new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("realm");
        return entryPoint;
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                //    in boot 2.0 password encoder is required
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser(userName).password(userPassword).roles(actuatorRole);
    }
}
