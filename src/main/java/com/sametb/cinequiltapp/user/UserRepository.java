package com.sametb.cinequiltapp.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsername(String username);

  Optional<User> findByUsernameOrEmail(String username, String email);

  void  deleteByUsername(String username);
}
