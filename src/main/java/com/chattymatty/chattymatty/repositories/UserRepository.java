package com.chattymatty.chattymatty.repositories;

import com.chattymatty.chattymatty.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
