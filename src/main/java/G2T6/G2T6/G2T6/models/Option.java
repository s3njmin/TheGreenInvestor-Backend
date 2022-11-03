package G2T6.G2T6.G2T6.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @NotNull(message = "Option Text should not be null")
    @Length(max=300)
    private String option;

    // @NotNull(message = "Feedback should not be null")
    @Length(max=300)
    private String feedback;

    // impact on GameStats
    @NotNull
    private int incomeImpact;
    @Min(0) @Max(100) @NotNull
    private int moraleImpact;
    @Min(0) @Max(100) @NotNull
    private int emissionImpact;

    @ManyToOne
    @JoinColumn(name="question_id", nullable=false)
    @JsonIgnore
    private Question question;

    public Option(final String option, final String feedback, final Question qn) {
        this.option = option;
        this.feedback = feedback;
        this.question = qn;
    }

    public Option(final String option, final String feedback, final Question qn, final int incomeImpact, final int moraleImpact ,final int emissionImpact) {
        this(option, feedback, qn);

        this.incomeImpact = incomeImpact;
        this.moraleImpact = moraleImpact;
        this.emissionImpact = emissionImpact;
    }
}
