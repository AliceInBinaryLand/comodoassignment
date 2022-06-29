package org.thejava.assignment.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.thejava.assignment.model.Status;
import org.thejava.assignment.model.Todo;
import org.thejava.assignment.model.User;

import java.util.List;

@Repository
public interface TodoServiceRepo extends CrudRepository<Todo, Long> {

//    @Query(value = "select * from groups, todos  where todos.group_id = groups.id and groups.creator_email = :email" , nativeQuery = true)
//    List<Todo> getUsersTodos(@Param("email") String email);

    List<Todo> getTodosByGroup_Creator_EmailAndStatus(String email, Status status);

}
