package G2T6.G2T6.G2T6.services;

import G2T6.G2T6.G2T6.models.Question;

import java.util.List;

public interface QuestionService {
    List<Question> listQuestions();
    List<Question> randomizedQuestions();
    Question getQuestion(final Long id);
    Question addQuestion(final Question question);
    Question updateQuestion(final Long id, final Question question);
    void deleteQuestion(final Long id);
}
