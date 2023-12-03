package com.sametb.cinequiltapp.user;

import com.sametb.cinequiltapp.favs.Favourite;
import com.sametb.cinequiltapp.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {


  @Id
  @GeneratedValue
  private Integer id;

  @Column(name = "username", unique = true, nullable = false, length = 100)
  private String username;

  @Column(name = "email", nullable = false, length = 100)
  private String email;

  @Column(name = "password", nullable = false, length = 100)
  private String password;

  @Column(name = "country", nullable = false, length = 100)
  private String country;

  @Column(name = "create_time", nullable = false)
  private LocalDateTime createTime;

  @Enumerated(EnumType.STRING) // 0,1 to "ADMIN", "USER"
  @Column(name = "role", nullable = false)
  private Role role;

//  @OneToMany(mappedBy = "user")
//  private List<Token> tokens;
//...
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Token> tokens;

//  public void addToken(Token token) {
//    if (tokens == null) {
//      tokens = new ArrayList<>();
//    }
//    tokens.add(token);
//    token.setUser(this);
//  }
//
//  public void removeToken(Token token) {
//    if (tokens != null) {
//      tokens.remove(token);
//      token.setUser(null);
//    }
//  }
//...

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username ;                     // todo: change email to username
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }



//  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//  private List<UserFavourites> favorites;

//  @ManyToMany(targetEntity = Metadata.class, cascade = CascadeType.ALL)
//  private List metadata;

//  @ManyToMany
//    @JoinTable(
//            name = "user_favourites",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "metadata_id"))
//  Set<Metadata> favourites;

//  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @OneToMany(mappedBy = "user")
  private List<Favourite> favourites;

}
