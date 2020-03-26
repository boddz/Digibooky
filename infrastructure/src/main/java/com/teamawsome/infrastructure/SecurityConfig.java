package com.teamawsome.infrastructure;

import com.teamawsome.infrastructure.authentication.BookstoreAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BookstoreAuthenticationEntryPoint authEntryPoint;
    private final BookstoreAuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(BookstoreAuthenticationEntryPoint authEntryPoint, BookstoreAuthenticationProvider authenticationProvider) {
        this.authEntryPoint = authEntryPoint;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

}
