package org.thejava.assignment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thejava.assignment.model.Status;
import org.thejava.assignment.model.Todo;
import org.thejava.assignment.model.Topics;
import org.thejava.assignment.model.User;
import org.thejava.assignment.service.api.MqServiceApi;
import org.thejava.assignment.service.api.TodoServiceApi;
import org.thejava.assignment.service.repository.TodoServiceRepo;

import java.util.List;

@Component("todoService")
public class TodoServiceImpl implements TodoServiceApi {

    @Autowired
    TodoServiceRepo todoServiceRepo;

    @Autowired
    MqServiceApi<Todo> todoMqService;

    @Override
    public boolean save(Todo todo) {
        boolean result = todoServiceRepo.save(todo).getId() != 0;
        if(result){
            if(todo.getStatus().equals(Status.Todo)){
                todoMqService.send(Topics.Todo.getTopic(), todo);
            }else{
                todoMqService.send(Topics.Done.getTopic(), todo);
            }
        }
        return result;
    }

    @Override
    public List<Todo> getTodos(User user, Status status) {
        return todoServiceRepo.getTodosByGroup_Creator_EmailAndStatus(user.getEmail(), status);
    }

    @Override
    public boolean delete(Todo todo) {
        todo.setStatus(Status.Deleted);
        boolean result = todoServiceRepo.save(todo).getId() != 0;
        if(result){
            todoMqService.send(Topics.Deleted.getTopic(), todo );
        }
        return result;
    }
}
