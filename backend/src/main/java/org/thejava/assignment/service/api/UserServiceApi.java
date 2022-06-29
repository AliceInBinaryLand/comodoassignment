package org.thejava.assignment.service.api;

import org.thejava.assignment.model.User;

public interface UserServiceApi{

    User getUser(User user);
    boolean save(User user);
    User getUserByJwtToken(String token);
}
