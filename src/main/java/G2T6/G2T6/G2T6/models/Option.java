package G2T6.G2T6.G2T6.models;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @NotNull(message = "Option Text should not be null")
    private String option;

    // @NotNull(message = "Feedback should not be null")
    private String feedback;

    @ManyToOne
    @JoinColumn(name="question_id", nullable=false)
    private Question question;

    public Option(final String option, final String feedback, final Question q) {
        this.option = option;
        this.feedback = feedback;
        this.question = q;
    }
}
