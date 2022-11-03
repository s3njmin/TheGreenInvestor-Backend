package G2T6.G2T6.G2T6.models.orders;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.*;

@Entity
@Getter
@Setter
@ToString
public class OptionOrder extends Order {
    // id is the primary key of option order in optionOrderRepository
    private @Id @GeneratedValue (strategy = GenerationType.IDENTITY) Long id;

    // verify indexArray is 4
    @Size(min = 4, max = 4)
    private ArrayList<Integer> indexArray;
    
    public OptionOrder() {
        // generate array of 1 to 6
        ArrayList<Integer> generatedArray = super.generateArray(6);  
        
        // set indexArray as manipulate array
        this.indexArray = manipulateArray(generatedArray);
    }
    
    @Override
    public ArrayList<Integer> manipulateArray(ArrayList<Integer> array){
        // store input array as manipulatedArray
        ArrayList<Integer> manipulatedArray = array;

        // shuffle their orders
        Collections.shuffle(manipulatedArray);

        // remove the first two elements in shuffled array 
        manipulatedArray.remove(0);
        manipulatedArray.remove(0);

        return manipulatedArray;
    }
}
