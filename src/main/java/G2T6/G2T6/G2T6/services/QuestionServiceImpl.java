package G2T6.G2T6.G2T6.services;

import java.util.List;

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
        if(sameQuestion.size() == 0) {
            return questions.save(question);
        }
        else
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
}
