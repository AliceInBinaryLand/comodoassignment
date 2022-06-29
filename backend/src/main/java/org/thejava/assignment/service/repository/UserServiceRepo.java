package org.thejava.assignment.service.repository;

import org.thejava.assignment.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserServiceRepo extends CrudRepository<User, Long> {

    @Query(value = "select * from users where email = :email" , nativeQuery = true)
    User getUserByEmail(@Param("email") String email);

}
