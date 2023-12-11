package com.example.BankService.config;

import com.example.BankService.services.userDetails.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/credit/**").hasAnyAuthority("USER", "EMPLOYEE", "ADMIN")
                .requestMatchers("/cards/**").hasAnyAuthority("USER", "EMPLOYEE", "ADMIN")
                .requestMatchers("/contribution/**").hasAnyAuthority("USER", "EMPLOYEE", "ADMIN")
                .requestMatchers("/profile").hasAnyAuthority("USER", "EMPLOYEE", "ADMIN")
                .requestMatchers("/profile/**").hasAnyAuthority("USER", "EMPLOYEE", "ADMIN")
                .requestMatchers("/logout").hasAnyAuthority("USER", "EMPLOYEE", "ADMIN")
                .requestMatchers("/register").permitAll()
                .requestMatchers("/**").permitAll()
                .and()
                .formLogin
                        (form -> form.loginPage("/loginM")
                                .permitAll()
                        )
                .userDetailsService(userDetailsService());

        return http.build();
    }
}
