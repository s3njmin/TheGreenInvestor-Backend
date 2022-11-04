package G2T6.G2T6.G2T6.models;

import G2T6.G2T6.G2T6.models.security.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GameStats implements Comparable<GameStats>{

    // game stats's id and primary key of the game stats database table
    @GeneratedValue(strategy = GenerationType.AUTO) @Id
    private Long id;

    // income value of game stats
    @NotNull
    private int incomeVal = 0;

    // morale value of game stats
    @Min(0) @Max(100) @NotNull
    private int moraleVal = 0;

    // emission value of game stats
    @Min(0) @Max(100) @NotNull
    private int emissionVal = 0;

    // current state for this game stats
    @OneToOne
    @JoinColumn(name = "currentState_id")
    private CurrentState currentState;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public GameStats(Long id){
        this.id = id;
    }

    public GameStats(int income, int morale, int emission, User user, CurrentState currentState){
        this.incomeVal = income;
        this.moraleVal = morale;
        this.emissionVal = emission;
        this.user = user;
        this.currentState = currentState;
    }

    public GameStats(int income, int morale, int emission){
        this.incomeVal = income;
        this.moraleVal = morale;
        this.emissionVal = emission;
    }


    public GameStats(int income, int morale, int emission, CurrentState currentState){
        this.incomeVal = income;
        this.moraleVal = morale;
        this.emissionVal = emission;
        this.currentState = currentState;
    }

    public void setCurrentState(CurrentState currentState) {
        this.currentState = currentState;
    }

    public CurrentState getCurrentState(){
        return this.currentState;
    }

    /**
     * sum of all game stats
     * @return to sum of income + morale + emission
     */
    public Long getTotal(){
        return (long)(incomeVal + moraleVal + emissionVal);
    }

    /**
     * comparing game stats
     * @param o a GameStats object
     * @return order of the comparison
     */
    @Override
    public int compareTo(GameStats o) {
        Long cV = getTotal();
        Long oV = o.getTotal();
        if(cV == oV) return 0;
        if(cV > oV) return -1;
        return 1;
    }

    public String toString(){
        return String.format("id - %d, total - ", id, getTotal());
    }
}
