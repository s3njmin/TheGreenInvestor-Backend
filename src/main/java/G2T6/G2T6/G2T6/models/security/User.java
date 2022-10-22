package G2T6.G2T6.G2T6.models.security;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import G2T6.G2T6.G2T6.models.GameStats;
import com.fasterxml.jackson.annotation.JsonIgnore;

import G2T6.G2T6.G2T6.models.CurrentState;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")
})

public class User {

  @NotNull
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
  private String password;

  @NotBlank
  private String role;

  //Shared primary key
//  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//  @PrimaryKeyJoinColumn
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonIgnore
  private List<CurrentState> currentState;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonIgnore
  private List<GameStats> gameStats;

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public User(String username, String email, String password, String role) {
    this(username, email, password);
    this.role = role;
  }
  public User(Long id, String username, String email, String password, String role) {
    this(username, email, password);
    this.role = role;
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
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

}
