package G2T6.G2T6.G2T6.models;

import G2T6.G2T6.G2T6.models.security.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GameStats implements Comparable<GameStats> {

    // game stats's id and primary key of the game stats database table
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonIgnore
    private Long id;

    // sustainability value of game stats
    @Min(-9999)
    @Max(9999)
    @NotNull
    private int emissionVal = 0;

    // morale value of game stats
    @Min(-9999)
    @Max(9999)
    @NotNull
    private int moraleVal = 0;

    // income value of game stats
    @Min(-9999)
    @Max(9999)
    @NotNull
    private int incomeVal = 0;

    // cost impact of game stats
    @Min(-9999)
    @Max(9999)
    @NotNull
    private int costVal = 0;

    // sustainability value of game stats
    @Min(-9999)
    @Max(9999)
    @NotNull
    private int currentEmissionVal = 150;

    // morale value of game stats
    @Min(-9999)
    @Max(9999)
    @NotNull
    private int currentMoraleVal = 65;

    // income value of game stats
    @Min(-9999)
    @Max(9999)
    @NotNull
    private int currentIncomeVal = 20;

    // cost impact of game stats
    @Min(-9999)
    @Max(9999)
    @NotNull
    private int currentCashInHand = 100;

    // multiplier
    private double multiplier = 1.0;

    // current state for this game stats
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "currentState_id")
    private CurrentState currentState;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public GameStats(Long id) {
        this.id = id;
    }

    public GameStats(int income, int morale, int emission, User user, CurrentState currentState) {
        this.incomeVal = income;
        this.moraleVal = morale;
        this.emissionVal = emission;
        this.user = user;
        this.currentState = currentState;
    }

    public GameStats(int income, int morale, int emission) {
        this.incomeVal = income;
        this.moraleVal = morale;
        this.emissionVal = emission;
    }

    public GameStats(int income, int morale, int emission, CurrentState currentState) {
        this.incomeVal = income;
        this.moraleVal = morale;
        this.emissionVal = emission;
        this.currentState = currentState;
    }

    public void setCurrentState(CurrentState currentState) {
        this.currentState = currentState;
    }

    public CurrentState getCurrentState() {
        return this.currentState;
    }

    public GameStats(int emission, int morale, int income, int cost, User user, CurrentState currentState,
            double multiplier, int currentEmission, int currentMorale, int currentIncome, int currentCashInHand) {
        this.incomeVal = income;
        this.moraleVal = morale;
        this.emissionVal = emission;
        this.costVal = cost;
        this.user = user;
        this.currentState = currentState;
        this.multiplier = multiplier;
        this.currentEmissionVal = currentEmission;
        this.currentMoraleVal = currentMorale;
        this.currentIncomeVal = currentIncome;
        this.currentCashInHand = currentCashInHand;
    }

    public GameStats(User user, CurrentState currentState,
            double multiplier, int currentEmission, int currentMorale, int currentIncome, int currentCashInHand) {
        this.user = user;
        this.currentState = currentState;
        this.multiplier = multiplier;
        this.currentEmissionVal = currentEmission;
        this.currentMoraleVal = currentMorale;
        this.currentIncomeVal = currentIncome;
        this.currentCashInHand = currentCashInHand;
    }

    /**
     * sum of all game stats
     * 
     * @return to sum of income + morale + emission
     */
    public Long getTotal() {
        return (long) (incomeVal + moraleVal + emissionVal);
    }

    /**
     * comparing game stats
     * 
     * @param o a GameStats object
     * @return order of the comparison
     */
    @Override
    public int compareTo(GameStats o) {
        Long cV = getTotal();
        Long oV = o.getTotal();
        if (cV == oV)
            return 0;
        if (cV > oV)
            return -1;
        return 1;
    }

    public String toString() {
        return String.format("id - %d, total - ", id, getTotal());
    }

}
