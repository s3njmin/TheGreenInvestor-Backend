package G2T6.G2T6.G2T6.models;

import java.util.List;

import javax.persistence.*;
import lombok.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Question {
    private @Id @GeneratedValue (strategy = GenerationType.IDENTITY) Long id;
    
    @NotNull(message = "Question should not be null")
    private String question;

    @NotNull(message = "Image location should not be null")
    private String imageLocation;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;

    //whether question is open-ended
    @NotNull(message = "Question type should not be null")
    private boolean isOpenEnded;

    public Question(final String qn) {
        this.question = qn;
    }

    public Question(final String qn, final Long id) {
        this.question = qn;
        this.id = id;
    }

    public Question(final String qn, final String imageLocation) {
        this.question = qn;
        this.imageLocation = imageLocation;
    }

    public Question(final String qn, final String imageLocation, final boolean isOpenEnded) {
        this.question = qn;
        this.imageLocation = imageLocation;
        this.isOpenEnded = isOpenEnded;
    }
    
}
