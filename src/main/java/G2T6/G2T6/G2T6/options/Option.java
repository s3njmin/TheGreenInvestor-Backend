package G2T6.G2T6.G2T6.options;

import G2T6.G2T6.G2T6.questions.Question;
import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Option {
    private @Id @GeneratedValue Long id;
    private String option;
    private String feedback;
    
    @ManyToOne
    @JoinColumn(name="question_id", nullable=false)

    private Question question;
}
