package G2T6.G2T6.G2T6.questions;

import java.util.List;

public interface QuestionService {
    List<Question> listQuestions();
    Question getQuestion(Long id);
    Question addQuestion(Question question);
    Question updateQuestion(Long id, Question question);
    void deleteQuestion(Long id);
}
