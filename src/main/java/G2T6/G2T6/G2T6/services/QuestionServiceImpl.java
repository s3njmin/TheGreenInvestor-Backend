package G2T6.G2T6.G2T6.services;

import java.util.*;

import G2T6.G2T6.G2T6.exceptions.OptionOrderIdInvalidException;
import G2T6.G2T6.G2T6.exceptions.QuestionNotFoundException;
import G2T6.G2T6.G2T6.models.Option;
import G2T6.G2T6.G2T6.models.Question;
import G2T6.G2T6.G2T6.models.orders.OptionOrder;
import G2T6.G2T6.G2T6.repository.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {
    private QuestionRepository questionRepository;
    private QuestionOrderService questionOrderService;
    private OptionOrderService optionOrderService;

    @Autowired
    public QuestionServiceImpl(final QuestionRepository questionRepository, final QuestionOrderService questionOrderService, final OptionOrderService optionOrderService) {
        this.questionRepository = questionRepository;
        this.questionOrderService = questionOrderService;
        this.optionOrderService = optionOrderService;
    }

    @Override
    public List<Question> listQuestions() {
        return questionRepository.findAll();
    }

    // 1) pick 8 out of 10 non open-ended questions
    // 2) shuffle order of 8 non open-ended + 2 open-ended
    // 2) for each question, select 4 out of 6 options, shuffle them
    @Override
    public List<Question> randomizedQuestions() {
        // get index array from random questionOrder returned
            // getQuestionOrder() throws QuestionOrderIdInvalidException if questionOrder is not found
        List<Integer> questionIndexes = questionOrderService.getQuestionOrder().getIndexArray();

        // get the list of questions
        List<Question> questions = questionRepository.findAll();

        // if (questions.get(0) != null) {
        //     return questions;
        // }

        // create arraylist to store randomized questions
        ArrayList<Question> randomizedQuestions = new ArrayList<>();

        for (int questionId : questionIndexes) {
            // get question corresponding to idx in questionOrder
            Question question = questions.get(questionId);
            
            if (question.getOptions().size() < 6) {
                // List<Question> error = new ArrayList<>();
                // error.add(question);
                // System.out.println("ID is : " + questionId);
                return questions;
                // throw new QuestionNotFoundException((long) questionId);
            }

            // if open ended question
            if (question.isOpenEnded()) {
                randomizedQuestions.add(question);
                continue;
            }

            // get randomly generated optionOrder from optionOrderService
                // will throw OptionOrderIdInvalidException if optionOrder is not found
            OptionOrder optionOrder = optionOrderService.getOptionOrder();

            // get index array from questionOrder
            List<Integer> optionIndexes = optionOrder.getIndexArray();

            // get list of options for current question
            List<Option> options = question.getOptions();


            // create arraylist to store randomized options
            List<Option> randomizedOptions = new ArrayList<>();

            for (int optionId : optionIndexes) {
                // if (options.size() < 4) {
                //     System.out.println(options.toString());
                // } else {
                // randomizedOptions.add(options.get(optionId));
                // }

                if (options != null && options.size() > 4) {
                    randomizedOptions.add(options.get(optionId));
                }
            }

            // update list of options after removing 2 options randomly
            question.setOptions(randomizedOptions);

            // add question to randomizedQuestions
            randomizedQuestions.add(question);
        }

        return randomizedQuestions();
    }



    @Override
    public Question getQuestion(final Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public Question addQuestion(final Question question) {
        List<Question> sameQuestion = questionRepository.findByQuestion(question.getQuestion());
        if(sameQuestion.size() == 0) {
            return questionRepository.save(question);
        }
        else
            return null;
    }

    @Override
    public Question updateQuestion(final Long id, final Question newQuestionInfo) {
        return questionRepository.findById(id).map(question -> {
            question.setQuestion(newQuestionInfo.getQuestion());
            return questionRepository.save(question);
        }).orElse(null);
    }

    @Override
    public void deleteQuestion(final Long id) {
        questionRepository.deleteById(id);
    }
}
