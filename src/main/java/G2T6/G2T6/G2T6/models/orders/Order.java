package G2T6.G2T6.G2T6.models.orders;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class Order {
    // id is the primary key of question order in questionOrderRepository
    private @Id @GeneratedValue (strategy = GenerationType.IDENTITY) Long id;

    // indexArray stores array of indexes
    private ArrayList<Integer> indexArray;

    // generates an array containing 1 to (size)
    public ArrayList<Integer> generateArray(int size) {
        // generate arraylist
        ArrayList<Integer> generatedArray = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            generatedArray.add(i);
        }

        return generatedArray;
    }

    // defined in children classes how to manipulate array
    public abstract ArrayList<Integer> manipulateArray(ArrayList<Integer> array);
}
