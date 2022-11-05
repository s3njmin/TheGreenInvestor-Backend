package G2T6.G2T6.G2T6.models;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

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
    
    // Question Id is Primary Key of Question Table
    private @Id @GeneratedValue (strategy = GenerationType.IDENTITY) Long id;
    
    // Question Body
    @NotNull(message = "Question should not be null")
    @Length(max=300)
    private String question;

    // Link to Image hosted on AWS
    @NotNull(message = "Image location should not be null")
    @URL(protocol = "https")
    private String imageLink;

    // List of options, one question to many options
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;

    // whether question is open-ended
    @NotNull(message = "Question type should not be null")
    private boolean isOpenEnded;

    public Question(final String qn) {
        this.question = qn;
    }

    // constructor - pass in question, imageLink, isOpenEnded
    public Question(final String qn, final String imageLink, final boolean isOpenEnded) {
        this.question = qn;
        this.imageLink = imageLink;
        this.isOpenEnded = isOpenEnded;
    }
}
