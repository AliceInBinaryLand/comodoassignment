package org.thejava.assignment.config;

import org.thejava.assignment.model.User;
import org.thejava.assignment.service.api.UserServiceApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Log4j2
@Configuration
@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @Autowired
    UserServiceApi userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication auth = null;
        User loginUser = new User();
        loginUser.setEmail(authentication.getName());
        loginUser.setPassword(authentication.getCredentials().toString());
        User registeredUser = userService.getUser(loginUser);
        if(registeredUser != null){
            auth = new UsernamePasswordAuthenticationToken(loginUser.getEmail() , loginUser.getPassword() , AuthorityUtils.NO_AUTHORITIES);

        }
        return auth;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass));

    }
}
