package org.thejava.assignment.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.thejava.assignment.model.User;
import org.thejava.assignment.service.api.UserServiceApi;
import org.thejava.assignment.service.repository.UserServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thejava.assignment.util.JwtTokenUtil;

@Service
@Component("userService")
public class UserServiceImpl implements UserServiceApi {

    @Autowired
    UserServiceRepo userServiceRepo;

    @Qualifier("jwtTokenUtil")
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public User getUser(User user) {
        return userServiceRepo.getUserByEmail(user.getEmail());
    }

    @Override
    public boolean save(User user) {
       return userServiceRepo.save(user) != null ? true : false;
    }

    @Override
    public User getUserByJwtToken(String token) {
        return userServiceRepo.getUserByEmail(jwtTokenUtil.getUserFromJwt(token));
    }


}
