package G2T6.G2T6.G2T6.services;

import java.util.List;

import G2T6.G2T6.G2T6.models.Question;
import G2T6.G2T6.G2T6.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {
    private QuestionRepository questions;

    public QuestionServiceImpl(QuestionRepository questions) {
        this.questions = questions;
    }

    @Override
    public List<Question> listQuestions() {
        return questions.findAll();
    }

    @Override
    public Question getQuestion(Long id) {
        return questions.findById(id).orElse(null);
    }

    @Override
    public Question addQuestion(Question question) {
        List<Question> sameQuestion = questions.findByQuestion(question.getQuestion());
        if(sameQuestion.size() == 0) {
            return questions.save(question);
        }
        else
            return null;
    }

    @Override
    public Question updateQuestion(Long id, Question newQuestionInfo) {
        return questions.findById(id).map(question -> {
            System.out.println("id: " + id + " | " + question.getQuestion());
            question.setQuestion(newQuestionInfo.getQuestion());
            System.out.println("- id: " + id + " | " + question.getQuestion());
            System.out.println("saving: " + questions.save(question));
            return questions.save(question);
        }).orElse(null);
    }

    @Override
    public void deleteQuestion(Long id) {
        questions.deleteById(id);
    }
}
