package G2T6.G2T6.G2T6.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import G2T6.G2T6.G2T6.exceptions.QuestionOrderIdInvalidException;
import G2T6.G2T6.G2T6.models.orders.QuestionOrder;
import G2T6.G2T6.G2T6.repository.QuestionOrderRepository;

import java.util.*;

@Service
public class QuestionOrderServiceImplementation implements QuestionOrderService {
    private QuestionOrderRepository questionOrders;

    @Autowired
    public QuestionOrderServiceImplementation(QuestionOrderRepository questionOrders) {
        this.questionOrders = questionOrders;
    }

    @Override
    public QuestionOrder getQuestionOrder() {
        // pick a questionOrder from 1 to 10 (we store 10 permutations of question orders)
        Random random = new Random();
        long questionOrderIdx = random.nextLong(10) + 1;

        // if questionOrder is found by ID, store in list of QuestionOrder
        ArrayList<QuestionOrder> questionOrderList = new ArrayList<>();
        questionOrders.findById(questionOrderIdx).ifPresent(questionOrderList::add);

        // if questionOrder at randomly generated index is not found, throw QuestionOrderIdInvalidException
        if (questionOrderList.isEmpty()) {
            throw new QuestionOrderIdInvalidException(questionOrderIdx);
        }

        // return randomly selected questionOrder
        return questionOrderList.get(0);
    }
}
