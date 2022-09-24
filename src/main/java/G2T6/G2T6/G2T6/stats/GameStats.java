package G2T6.G2T6.G2T6.stats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GameStats {
    @GeneratedValue @Id
    private Long id;

    private List<Stats> gameStats = null;


}
