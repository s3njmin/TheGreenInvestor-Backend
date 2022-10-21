package G2T6.G2T6.G2T6.models.random;

import java.util.ArrayList;

import javax.persistence.*;
import lombok.*;

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
