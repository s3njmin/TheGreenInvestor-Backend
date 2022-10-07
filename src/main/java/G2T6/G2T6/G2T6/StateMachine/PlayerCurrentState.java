package G2T6.G2T6.G2T6.StateMachine;

import G2T6.G2T6.G2T6.stats.GameStats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PlayerCurrentState {
    @GeneratedValue(strategy = GenerationType.AUTO) @Id
    private Long id;
    @Min(0) @Max(10) @NotNull
    private int yearValue;
    @Enumerated(EnumType.STRING) @NotNull
    private State currentState;

    @OneToMany(mappedBy = "playerCurrentState", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<GameStats> gameStats;

    public PlayerCurrentState(long id, int year, State state) {
        this.id = id;
        this.yearValue = year;
        this.currentState = state;
    }

    public PlayerCurrentState(int year, State state) {
        this.yearValue = year;
        this.currentState = state;
    }

    public void changeState(State state){
        this.currentState = state;
    }
}
