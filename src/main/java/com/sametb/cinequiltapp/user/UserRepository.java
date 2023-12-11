package com.sametb.cinequiltapp.user;

import com.sametb.cinequiltapp.metadata.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);



  User findByUsername(String username);

  //todo: username not declared in User.java

  Optional<User> findByUsernameOrEmail(String username, String email);

  void  deleteByUsername(String username);




//@Query("SELECT F FROM USER_FAVOURITES F WHERE user_id = 3 group by user_id")
//  List<Metadata> allFavourites;


}
