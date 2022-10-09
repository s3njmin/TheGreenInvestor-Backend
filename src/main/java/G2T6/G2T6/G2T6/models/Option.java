package G2T6.G2T6.G2T6.models;

import java.io.Serializable;

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

    private @Id @GeneratedValue (strategy = GenerationType.AUTO) Long id;
    // @EmbeddedId
    // private CompositeId id;

    private String option;
    private String feedback;

    @ManyToOne
    @JoinColumn(name="question_id", nullable=false)
    private Question question;
}
