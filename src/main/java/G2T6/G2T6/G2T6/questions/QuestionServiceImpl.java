package G2T6.G2T6.G2T6.questions;

import java.util.List;
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
        List<Question> sameQuestion = questions.findByTitle(question.getQuestion());
        if(sameQuestion.size() == 0)
            return questions.save(question);
        else
            return null;
    }

    @Override
    public Question updateQuestion(Long id, Question newQuestionInfo) {
        return questions.findById(id).map(question -> {question.setQuestion(newQuestionInfo.getQuestion());
            return questions.save(question);
        }).orElse(null);
    }

    @Override
    public void deleteQuestion(Long id) {
        questions.deleteById(id);
    }
}
