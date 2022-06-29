package org.thejava.assignment.controller;

import org.thejava.assignment.model.User;
import org.thejava.assignment.service.api.UserServiceApi;
import org.thejava.assignment.util.JwtTokenUtil;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@EnableWebSecurity
@CrossOrigin(origins = "*")
@RequestMapping("api/secured/")
public class UserController {

    @Autowired
    UserServiceApi userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "registerUser")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String  registerUser(@RequestBody User user) throws Exception {
        if(userService.getUser(user) != null){
            return "User is already registered";
        }else{
            user.setPassword( passwordEncoder.encode(user.getPassword()) );
            return ""+ userService.save(user);
        }
    }

    @PostMapping(value = "doLogin")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    JSONObject doLogin(@RequestBody User user) throws Exception {
        JSONObject js = new JSONObject();
        try{
            Authentication authToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), AuthorityUtils.NO_AUTHORITIES);
            authToken = authenticationManager.authenticate(authToken);
            if(authToken != null){
                SecurityContextHolder.getContext().setAuthentication(authToken);
                js.put("jwt" , jwtTokenUtil.generateJwtToken(authToken));
                js.put("result" , true);
            }else{
                js.put("result", false);
            }
            return js;
        }catch (Exception e){
            js.put("result", false);
            e.printStackTrace();
            return js;
        }
    }


}
