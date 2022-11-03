package G2T6.G2T6.G2T6.services;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import G2T6.G2T6.G2T6.models.Option;
import G2T6.G2T6.G2T6.models.Question;
import G2T6.G2T6.G2T6.repository.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {
    private QuestionRepository questions;
    // private OptionRepository options;

    @Autowired
    public QuestionServiceImpl(final QuestionRepository questions) {
        this.questions = questions;
    }

    @Override
    public List<Question> listQuestions() {
        return questions.findAll();
    }

    @Override
    public Question getQuestion(final Long id) {
        return questions.findById(id).orElse(null);
    }

    @Override
    public Question addQuestion(final Question question) {
        List<Question> sameQuestion = questions.findByQuestion(question.getQuestion());
        if (sameQuestion.size() == 0) {
            return questions.save(question);
        } else
            return null;
    }

    @Override
    public Question updateQuestion(final Long id, final Question newQuestionInfo) {
        return questions.findById(id).map(question -> {
            // System.out.println("id: " + id + " | " + question.getQuestion());
            question.setQuestion(newQuestionInfo.getQuestion());
            // System.out.println("- id: " + id + " | " + question.getQuestion());
            // System.out.println("saving: " + questions.save(question));
            return questions.save(question);
        }).orElse(null);
    }

    @Override
    public void deleteQuestion(final Long id) {
        questions.deleteById(id);
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
