package G2T6.G2T6.G2T6.models.security;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import G2T6.G2T6.G2T6.models.GameStats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import G2T6.G2T6.G2T6.config.CustomAuthorityDeserializer;
import G2T6.G2T6.G2T6.models.CurrentState;

import java.util.*;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")
})
public class User implements UserDetails {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  @JsonIgnore
  private String password;

  @NotBlank
  private String role;

  @NotNull
  private boolean isSubscribedEmail;

  @Min(1)
  @Max(10)
  private int profileImageIndex = 1;

  // High score
  private double highScore = 0;

  // number of games played
  private int gamesPlayed = 0;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JsonIgnore
  private List<CurrentState> currentState;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JsonIgnore
  private List<GameStats> gameStats;

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public User(String username, String email, String password, boolean isSubscribedEmail) {
    this(username, email, password);
    this.isSubscribedEmail = isSubscribedEmail;
  }

  public User(String username, String email, String password, String role, boolean isSubscribedEmail) {
    this(username, email, password);
    this.role = role;
    this.isSubscribedEmail = isSubscribedEmail;
  }

  public boolean isSubscribedEmail() {
    return isSubscribedEmail;
  }

  public void setSubscribedEmail(boolean isSubscribedEmail) {
    this.isSubscribedEmail = isSubscribedEmail;
  }

  public User(Long id, String username, String email, String password, String role) {
    this(username, email, password);
    this.role = role;
    this.id = id;
  }

  public User(String username, String email, String password, String role) {
    this(username, email, password);
    this.role = role;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<CurrentState> getCurrentState() {
    return currentState;
  }

  public void setCurrentState(List<CurrentState> currentState) {
    this.currentState = currentState;
  }

  public List<GameStats> getGameStats() {
    return gameStats;
  }

  public void setGameStats(List<GameStats> gameStats) {
    this.gameStats = gameStats;
  }

  public int getProfileImageIndex() {
    return profileImageIndex;
  }

  public void setProfileImageIndex(int profileImageIndex) {
    this.profileImageIndex = profileImageIndex;
  }

  public double getHighScore() {
    return highScore;
  }

  public void setHighScore(double highScore) {
    this.highScore = highScore;
  }

  public int getGamesPlayed() {
    return gamesPlayed;
  }

  public void setGamesPlayed(int gamesPlayed) {
    this.gamesPlayed = gamesPlayed;
  }

  @JsonDeserialize(using = CustomAuthorityDeserializer.class)
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.asList(new SimpleGrantedAuthority(role));
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", role='" + role + '\'' +
        ", isSubscribedEmail=" + isSubscribedEmail +
        ", profileImageIndex=" + profileImageIndex +
        ", highScore=" + highScore +
        ", gamesPlayed=" + gamesPlayed +
        ", currentState=" + currentState +
        ", gameStats=" + gameStats +
        '}';
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

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

}