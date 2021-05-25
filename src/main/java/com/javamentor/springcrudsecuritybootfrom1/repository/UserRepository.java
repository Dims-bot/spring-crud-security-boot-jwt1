package com.javamentor.springcrudsecuritybootfrom1.repository;

import com.javamentor.springcrudsecuritybootfrom1.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("select user from User user join fetch user.roles where user.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("select distinct u from User u join fetch u.roles")
    List<User> getAllUsers();

    Boolean existsByUsername(String username);








}

