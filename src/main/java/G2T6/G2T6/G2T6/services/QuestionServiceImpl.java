package G2T6.G2T6.G2T6.services;

import java.util.*;

import G2T6.G2T6.G2T6.models.Option;
import G2T6.G2T6.G2T6.models.Question;
import G2T6.G2T6.G2T6.models.orders.OptionOrder;
import G2T6.G2T6.G2T6.repository.QuestionRepository;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
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
            Question randomizedQuestion = question;
            randomizedQuestion.setOptions(randomizedOptions);

            // add question to randomizedQuestions
            randomizedQuestions.add(randomizedQuestion);
        }

        return randomizedQuestions;
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

    @Override
    public List<Question> initQuestions() {

        // get the list of questions
        List<Question> questions = listQuestions();

        // remove 2 out of first 10 questions (non-open ended)
        Random random = new Random();
        questions.remove(random.nextInt(10));
        questions.remove(random.nextInt(9));

        // shuffle their orders
        Collections.shuffle(questions);

        for (Question question : questions) {
            // get list of options for each question in list
            List<Option> options = question.getOptions();
            int numOptions = options.size();

            // if NOT open ended question, randomly remove 2 options
            if (!question.isOpenEnded() && numOptions > 4) {
                // remove until number of options <= 4
                while (numOptions > 4) {
                    options.remove(random.nextInt(numOptions));
                    numOptions--;
                }
            }

            // update list of options after removing 2 options randomly
            question.setOptions(options);
        }

        return questions;

    }
}
