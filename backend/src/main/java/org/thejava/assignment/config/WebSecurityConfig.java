package org.thejava.assignment.config;

import org.thejava.assignment.util.AuthenticationUnsuccessfulHandler;
import org.thejava.assignment.util.JwtFilter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Log4j2
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    AuthenticationUnsuccessfulHandler authenticationUnsuccessfulHandler;


    @Autowired
    CustomAuthProvider customAuthProvider;


    @Autowired
    JwtFilter jwtFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and().sessionManagement().sessionFixation().migrateSession()
                .and().httpBasic()
                .and()
                .authorizeRequests().antMatchers("/api/unsecured/**").permitAll()
                .and()
                .cors().disable()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationUnsuccessfulHandler);
        http.sessionManagement().maximumSessions(1);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.error("auth manager was registered");
        auth.authenticationProvider(customAuthProvider);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
