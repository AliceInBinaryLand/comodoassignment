package org.thejava.assignment.service.api;

import org.thejava.assignment.model.Group;
import org.thejava.assignment.model.Status;
import org.thejava.assignment.model.Todo;
import org.thejava.assignment.model.User;

import java.util.List;

public interface TodoServiceApi {

    boolean save(Todo todo);

    List<Todo> getTodos(User user, Status status);

    boolean delete(Todo todo);


}
