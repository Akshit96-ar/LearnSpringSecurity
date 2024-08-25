package com.java.learning.LearnSpringSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;  // need to implement this class

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("register","login")
                        .permitAll()
                        .anyRequest().authenticated())
                //.formLogin(Customizer.withDefaults())  // for form login
                .httpBasic(Customizer.withDefaults())  // for postman
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    // This bean will create multiple user in place of single used via app.yaml. Works the same way.
    // please note existing user in app.yml will not work when using this bean.
    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails userA = User.withDefaultPasswordEncoder()
                .username("IntegrationUserA")
                .password("@14cse")
                .roles("USER")
                .build();

        UserDetails userB = User.withDefaultPasswordEncoder()
                .username("IntegrationUserB")
                .password("@15cse")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(userA, userB);
    }*/


    /* Actual DB authentication Bean */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
