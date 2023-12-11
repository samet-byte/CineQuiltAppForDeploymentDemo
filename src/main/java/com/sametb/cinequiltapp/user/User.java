package com.sametb.cinequiltapp.user;

import com.sametb.cinequiltapp.fav.Favourite;
import com.sametb.cinequiltapp.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

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

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Token> tokens;



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


//  @ManyToMany(cascade = CascadeType.PERSIST)
//    @JoinTable(
//            name = "user_metadata",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "metadata_id"))
//    private Set<Metadata> metadata = new HashSet<>();


  @OneToMany(
          mappedBy = "user",
          cascade = {
                  CascadeType.PERSIST,
                  CascadeType.DETACH,
                  CascadeType.REFRESH,
          })
  private Set<Favourite> favourites;


}
