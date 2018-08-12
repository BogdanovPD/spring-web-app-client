package com.bogdanovpd.spring.webapp.spring.webapp.client.config;

import com.bogdanovpd.spring.webapp.spring.webapp.client.util.UserAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/admin**").hasAuthority("ROLE_ADMIN")
                .and()
                .formLogin().loginPage("/login")
                .failureUrl("/login?error")
                .usernameParameter("login")
                .passwordParameter("pass")
                .successHandler(new UserAuthenticationSuccessHandler())
                .and()
                .logout().logoutSuccessUrl("/login")
                .and()
                .csrf().disable();
    }
}
