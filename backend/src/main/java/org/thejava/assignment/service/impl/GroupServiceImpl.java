package org.thejava.assignment.service.impl;

import org.springframework.security.core.context.SecurityContext;
import org.thejava.assignment.model.Group;
import org.thejava.assignment.model.User;
import org.thejava.assignment.service.api.GroupServiceApi;
import org.thejava.assignment.service.api.UserServiceApi;
import org.thejava.assignment.service.repository.GroupServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component("groupService")
public class GroupServiceImpl implements GroupServiceApi {

    @Autowired
    GroupServiceRepo groupServiceRepo;

    @Autowired
    UserServiceApi userService;

    @Override
    public boolean save(Group group) {
        Group g = groupServiceRepo.save(group);
        return g.getId() != 0 ? true : false;
    }

    @Override
    public List<Group> getGroups(User user) {
        List<Group> groups = groupServiceRepo.getUsersGroup(user.getEmail());
        return groups;
    }

    @Override
    public boolean delete(Group group) {
        groupServiceRepo.delete(group);
        return !groupServiceRepo.existsById(group.getId());
    }
}
