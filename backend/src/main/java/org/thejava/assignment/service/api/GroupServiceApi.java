package org.thejava.assignment.service.api;

import org.thejava.assignment.model.Group;
import org.thejava.assignment.model.User;

import java.util.List;

public interface GroupServiceApi {

    boolean save(Group group);

    List<Group> getGroups(User user);

    boolean delete(Group group);

}
