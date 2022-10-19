package G2T6.G2T6.G2T6.models.random;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
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
public class QuestionOrder {
    private @Id @GeneratedValue (strategy = GenerationType.IDENTITY) Long id;
    
    private ArrayList<Integer> indexArray;

    public QuestionOrder(ArrayList<Integer> indexArray) {
        this.indexArray = indexArray;        
    }
}
