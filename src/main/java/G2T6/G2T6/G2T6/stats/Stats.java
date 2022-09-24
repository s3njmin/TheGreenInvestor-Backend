package G2T6.G2T6.G2T6.stats;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Stats {

    @Min(0)
    @Max(100)
    @NotNull
    private int statsValue;
    @NotNull
    private String statsName;
    @NotNull
    private String statsDescription;

    public Stats(int val, String name, String description){
        this.statsValue = val;
        this.statsName = name;
        this.statsDescription = description;
    }

    public void increaseValue(Integer increaseVal){
        this.statsValue += increaseVal;
    }

    public void decreaseValue(Integer decreaseValue){
        this.statsValue -= decreaseValue;
    }
}
