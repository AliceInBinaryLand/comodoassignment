package org.thejava.assignment.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;
import org.thejava.assignment.model.Group;
import org.thejava.assignment.model.Status;
import org.thejava.assignment.model.Todo;
import org.thejava.assignment.model.User;
import org.thejava.assignment.service.api.TodoServiceApi;
import org.thejava.assignment.service.api.UserServiceApi;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@EnableWebSecurity
@CrossOrigin(origins = "*")
@RequestMapping("api/secured/todos/")
public class TodoController {

    @Autowired
    TodoServiceApi todoService;

    @Autowired
    UserServiceApi userService;

    @PostMapping(value = "save")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    boolean save(@RequestBody Todo todo, HttpServletRequest request) throws Exception {
        todo.setStatus(Status.Todo);
        return todoService.save(todo);
    }

    @PostMapping(value = "getTodos")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Todo> getTodos(HttpServletRequest request) throws Exception {
        User user = userService.getUserByJwtToken(request.getHeader("Authorization"));
        return todoService.getTodos(user , Status.Todo);
    }

    @PostMapping(value = "getCompletedTodos")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Todo> getCompletedTodos(HttpServletRequest request) throws Exception {
        User user = userService.getUserByJwtToken(request.getHeader("Authorization"));
        return todoService.getTodos(user , Status.Done);
    }

    @PostMapping(value = "delete")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    boolean delete( @RequestBody Todo todo  ) throws Exception {
        return  todoService.delete(todo);
    }
    @PostMapping(value = "done")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    boolean done( @RequestBody Todo todo  ) throws Exception {
        todo.setStatus(Status.Done);
        return  todoService.save(todo);
    }

}
