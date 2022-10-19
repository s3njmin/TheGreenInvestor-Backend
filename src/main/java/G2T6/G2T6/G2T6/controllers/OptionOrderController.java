package G2T6.G2T6.G2T6.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

import G2T6.G2T6.G2T6.exceptions.OptionOrderNotFoundException;
import G2T6.G2T6.G2T6.models.random.OptionOrder;
import G2T6.G2T6.G2T6.repository.OptionOrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class OptionOrderController {
    private OptionOrderRepository optionOrderRepository;

    @Autowired
    public OptionOrderController(final OptionOrderRepository optionOrderRepository){
        this.optionOrderRepository = optionOrderRepository;
    }

    @GetMapping("/optionOrder")
    public Optional<OptionOrder> getOptionOrder() {
        // generate Long from 1 - 10
        Random rand = new Random();
        Long randomLong = rand.nextLong(10) + 1;

        Optional<OptionOrder> optionOrder = optionOrderRepository.findById(randomLong);

        if (optionOrder.isEmpty()) {
            throw new OptionOrderNotFoundException(randomLong);
        }

        return optionOrder;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/optionOrder")
    public OptionOrder addOptionOrder(){
       
        // create list of 10 randomly ordered numbers
        ArrayList<Integer> indexArray = new ArrayList<>();
        Collections.addAll(indexArray, 1, 2, 3, 4, 5, 6);
        Collections.shuffle(indexArray);

        // remove last 2 elements of ArrayList
        indexArray.remove(5);
        indexArray.remove(4);

        
        // create new optionOrder with order in indexArray (not alr in repo)
        OptionOrder optionOrder = new OptionOrder(indexArray);

        // add to optionOrderRepository
        optionOrderRepository.save(optionOrder);

        return optionOrder;
    }
}
