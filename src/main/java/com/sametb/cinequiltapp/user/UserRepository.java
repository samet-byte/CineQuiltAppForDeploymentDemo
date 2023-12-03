package com.sametb.cinequiltapp.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);



  Optional<User> findByUsername(String username);
  //todo: username not declared in User.java

  Optional<User> findByUsernameOrEmail(String username, String email);

  void  deleteByUsername(String username);



}
