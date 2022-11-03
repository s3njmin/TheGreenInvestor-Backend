package G2T6.G2T6.G2T6.models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OptionReturn {
    private Long id;
    private String feedback;

    private int incomeImpact;
    private int moraleImpact;
    private int emissionImpact;
}
