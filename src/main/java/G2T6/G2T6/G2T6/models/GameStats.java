package G2T6.G2T6.G2T6.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GameStats implements Comparable<GameStats>{

    @GeneratedValue(strategy = GenerationType.AUTO) @Id
    private Long id;

    @NotNull
    private int incomeVal = 0;

    @Min(0) @Max(100) @NotNull
    private int moraleVal = 0;

    @Min(0) @Max(100) @NotNull
    private int emissionVal = 0;

    @ManyToOne
    @JoinColumn(name = "currentState_id")
    private CurrentState currentState;

    public GameStats(Long id){
        this.id = id;
    }

    public Long getTotal(){
        return (long)(incomeVal + moraleVal + emissionVal);
    }

    @Override
    public int compareTo(GameStats o) {
        Long cV = getTotal();
        Long oV = o.getTotal();
        if(cV == oV) return 0;
        if(cV > oV) return -1;
        return 1;
    }
}
