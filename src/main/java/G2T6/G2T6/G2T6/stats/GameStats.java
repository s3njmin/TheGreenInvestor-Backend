package G2T6.G2T6.G2T6.stats;

import G2T6.G2T6.G2T6.StateMachine.PlayerCurrentState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GameStats {

    @GeneratedValue(strategy = GenerationType.AUTO) @Id
    private Long id;

    @NotNull
    private int incomeVal = 0;

    @Min(0) @Max(100) @NotNull
    private int moraleVal = 0;

    @Min(0) @Max(100) @NotNull
    private int emissionVal = 0;

    @ManyToOne
    @JoinColumn(name = "playerCurrentState_id")
    private PlayerCurrentState playerCurrentState;

    public GameStats(Long id){
        this.id = id;
    }

}
