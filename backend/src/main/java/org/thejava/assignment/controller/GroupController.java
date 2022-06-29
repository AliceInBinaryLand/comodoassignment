package org.thejava.assignment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;
import org.thejava.assignment.model.Group;
import org.thejava.assignment.model.User;
import org.thejava.assignment.service.api.GroupServiceApi;
import org.thejava.assignment.service.api.UserServiceApi;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@EnableWebSecurity
@CrossOrigin(origins = "*")
@RequestMapping("api/secured/groups/")
public class GroupController {

    @Autowired
    GroupServiceApi groupService;

    @Autowired
    UserServiceApi userService;

    @PostMapping(value = "save")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    boolean  save(@RequestBody Group group , HttpServletRequest request) throws Exception {
        User user = userService.getUserByJwtToken(request.getHeader("Authorization"));
        group.setCreator(user);
        return groupService.save(group);
    }

    @PostMapping(value = "getGroups")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Group> getGroups(  HttpServletRequest request ) throws Exception {
        User user = userService.getUserByJwtToken(request.getHeader("Authorization"));
        return groupService.getGroups(user);
    }

    @PostMapping(value = "delete")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    boolean delete( @RequestBody Group group  ) throws Exception {
        return  groupService.delete(group);
    }
}
