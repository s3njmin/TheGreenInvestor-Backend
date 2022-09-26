package G2T6.G2T6.G2T6.questions;

import java.util.List;

import G2T6.G2T6.G2T6.options.Option;
import javax.persistence.*;
import lombok.*;

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
    private String question;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore

    private List<Option> options;

    public Question(String qn) {
        this.question = qn;
    }
}
