package G2T6.G2T6.G2T6.models;

import G2T6.G2T6.G2T6.misc.State;

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
public class CurrentState {
    @Id
    private Long id;
    @Min(0) @Max(10) @NotNull
    private int yearValue;
    @Enumerated(EnumType.STRING) @NotNull
    private State currentState;

    @OneToMany(mappedBy = "currentState", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<GameStats> gameStats;

    public CurrentState(long id, int year, State state) {
        this.id = id;
        this.yearValue = year;
        this.currentState = state;
    }

    public CurrentState(int year, State state) {
        this.yearValue = year;
        this.currentState = state;
    }

    public void changeState(State state){
        this.currentState = state;
    }
}
