package com.penguin.penguincoco.config.security;

import com.penguin.penguincoco.config.security.authority.AccessDeniedHandlerImpl;
import com.penguin.penguincoco.config.security.login.AuthenticationEntryPointImpl;
import com.penguin.penguincoco.config.security.login.LoginAuthenticationFilter;
import com.penguin.penguincoco.config.security.login.LoginFailureHandlerImpl;
import com.penguin.penguincoco.config.security.login.LoginSuccessHandlerImpl;
import com.penguin.penguincoco.config.security.logout.LogoutSuccessHandlerImpl;
import com.penguin.penguincoco.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private SecurityService securityService;

    @Autowired
    public SecurityConfig(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointImpl())
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
                .and()
                .addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.logout()
                .logoutUrl("/api/logout")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandlerImpl());
        filter.setAuthenticationFailureHandler(new LoginFailureHandlerImpl());
        filter.setFilterProcessesUrl("/api/login");
        return filter;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
