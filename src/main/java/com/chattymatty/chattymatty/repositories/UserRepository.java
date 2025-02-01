package com.chattymatty.chattymatty.repositories;

import com.chattymatty.chattymatty.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameAndIsActiveIsGreaterThan(String username, int isActiveIsGreaterThan);

    List<User> findByUsernameContainingAndIsActiveIsGreaterThan(String username, int isActiveIsGreaterThan);

    List<User> findAllByIsActiveIsGreaterThan(int isActiveIsGreaterThan);
}
