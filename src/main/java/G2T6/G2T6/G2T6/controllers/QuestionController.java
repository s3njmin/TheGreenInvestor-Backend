package G2T6.G2T6.G2T6.controllers;

import java.util.*;

import javax.validation.Valid;

import G2T6.G2T6.G2T6.exceptions.OptionOrderIdInvalidException;
import G2T6.G2T6.G2T6.exceptions.QuestionExistsException;
import G2T6.G2T6.G2T6.exceptions.QuestionNotFoundException;
import G2T6.G2T6.G2T6.exceptions.QuestionOrderIdInvalidException;
import G2T6.G2T6.G2T6.models.Option;
import G2T6.G2T6.G2T6.models.Question;
import G2T6.G2T6.G2T6.models.orders.OptionOrder;
import G2T6.G2T6.G2T6.models.orders.QuestionOrder;
import G2T6.G2T6.G2T6.repository.OptionOrderRepository;
import G2T6.G2T6.G2T6.repository.QuestionOrderRepository;
import G2T6.G2T6.G2T6.services.QuestionService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class QuestionController {
    private QuestionService questionService;
    private QuestionOrderRepository questionOrders;
    private OptionOrderRepository optionOrders;
    
    @Autowired
    public QuestionController(final QuestionService questionService, QuestionOrderRepository questionOrders, OptionOrderRepository optionOrders){
        this.questionService = questionService;
        this.questionOrders = questionOrders;
        this.optionOrders = optionOrders;
    }

    // return all questions & options (called from frontend & start of game)
    @GetMapping("/questionsAndOptions")
    public List<Question> getQuestionAndOptions() {
        
        // pick a questionOrder from 1 to 10 (we store 10 permutations of question orders)
        Random random = new Random();
        long questionOrderIdx = random.nextLong(10) + 1;

        // if questionOrder is found by ID, store in list of QuestionOrder
        ArrayList<QuestionOrder> questionOrderList = new ArrayList<>();
        questionOrders.findById(questionOrderIdx).ifPresent(questionOrderList::add);

        List<Integer> questionOrder;

        // if one questionOrder is found, get index array
        if (questionOrderList.size() == 1) {
            questionOrder = questionOrderList.get(0).getIndexArray();
        } else {
            throw new QuestionOrderIdInvalidException(questionOrderIdx);
        }

        // get the list of questions
        List<Question> questions = questionService.listQuestions();
        ArrayList<Question> randomizedQuestions = new ArrayList<>();

        for (int questionId : questionOrder) {
            // get question corresponding to idx in questionOrder
            Question question = questions.get(questionId);

            // if open ended question
            if (question.isOpenEnded()) {
                randomizedQuestions.add(question);
                continue;
            }

            // get list of options for each question in list
            List<Option> options = question.getOptions();

            // pick an optionOrder from 1 to 10 (we store 10 permutations of option orders)
            long optionOrderIdx = random.nextLong(10) + 1;

            // if optionOrder is found by ID, store in list of OptionOrder
            ArrayList<OptionOrder> optionOrderList = new ArrayList<>();
            optionOrders.findById(optionOrderIdx).ifPresent(optionOrderList::add);

            List<Integer> optionOrder;

            // if one optionOrder is found, get index array
            if (optionOrderList.size() == 1) {
                optionOrder = optionOrderList.get(0).getIndexArray();
            } else {
                throw new OptionOrderIdInvalidException(questionOrderIdx);
            }

            List<Option> randomizedOptions = new ArrayList<>();

            for (int optionId : optionOrder) {
                randomizedOptions.add(options.get(optionId));
            }

            // update list of options after removing 2 options randomly
            question.setOptions(randomizedOptions);

            // add question to randomizedQuestions
            randomizedQuestions.add(question);
        }

        return randomizedQuestions;
    }

    // return all questions
    @GetMapping("/questions")
    public List<Question> getQuestion() {
        return questionService.listQuestions();
    }

    @GetMapping("/questions/{id}")
    public Question getQuestion(@PathVariable final Long id) {
        Question question = questionService.getQuestion(id);
        if(question == null) throw new QuestionNotFoundException(id);
        return questionService.getQuestion(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/questions")
    public Question addQuestion(@RequestBody final Question question){
        Question savedQuestion = questionService.addQuestion(question);
        if (savedQuestion ==  null) throw new QuestionExistsException(question.getQuestion());
        return savedQuestion;
    }

    @PutMapping("/questions/{id}")
    public Question updateQuestion(@PathVariable final Long id, @Valid @RequestBody final Question newQuestionInfo){
        Question question = questionService.updateQuestion(id, newQuestionInfo);
        if(question == null) throw new QuestionNotFoundException(id);
        return question;
    }

    @DeleteMapping("/questions/{id}")
    public void deleteQuestion(@PathVariable final Long id){
        try{
            questionService.deleteQuestion(id);
        } catch(EmptyResultDataAccessException e) {
            throw new QuestionNotFoundException(id);
        }
    }
}