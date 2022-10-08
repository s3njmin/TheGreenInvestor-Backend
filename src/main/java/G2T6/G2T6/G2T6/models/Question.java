package G2T6.G2T6.G2T6.models;

import java.util.List;

import javax.persistence.*;
import lombok.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    private String imageLocation;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore

    private List<Option> options;

    public Question(String qn) {
        this.question = qn;
    }

    public Question(String qn, Long id) {
        this.question = qn;
        this.id = id;
    }
}
