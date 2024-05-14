package com.utc2.it.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.utc2.it.Ecommerce.entity.Role;
import com.utc2.it.Ecommerce.entity.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    User findByRole(Role role);
    @Query("select  u from User u where u.email=:email ")
    User findUserByEmail(@Param("email")String email);

}
