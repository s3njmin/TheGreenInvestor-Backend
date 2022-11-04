package G2T6.G2T6.G2T6.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import G2T6.G2T6.G2T6.exceptions.OptionOrderIdInvalidException;
import G2T6.G2T6.G2T6.models.orders.OptionOrder;
import G2T6.G2T6.G2T6.repository.OptionOrderRepository;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OptionOrderServiceImplementation implements OptionOrderService {
    private OptionOrderRepository optionOrders;

    @Autowired
    public OptionOrderServiceImplementation(OptionOrderRepository optionOrders) {
        this.optionOrders = optionOrders;
    }

    @Override
    public OptionOrder getOptionOrder() {
        // pick a optionOrder from 1 to 10 (we store 10 permutations of option orders)
        Random random = new Random();
        // long optionOrderIdx = random.nextLong(10) + 1;
        // long optionOrderIdx = ThreadLocalRandom.current().nextLong(10) + 1;
        long optionOrderIdx = ThreadLocalRandom.current().nextLong(10) + 1;

        // if optionOrder is found by ID, store in list of OptionOrder
        ArrayList<OptionOrder> optionOrderList = new ArrayList<>();
        optionOrders.findById(optionOrderIdx).ifPresent(optionOrderList::add);

        // if optionOrder at randomly generated index is not found, throw OptionOrderIdInvalidException
        if (optionOrderList.isEmpty()) {
            throw new OptionOrderIdInvalidException(optionOrderIdx);
        }

        // return randomly selected optionOrder
        return optionOrderList.get(0);
    }
}