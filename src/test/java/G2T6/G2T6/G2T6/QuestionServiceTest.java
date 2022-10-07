package G2T6.G2T6.G2T6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import G2T6.G2T6.G2T6.questions.*;

@ExtendWith(MockitoExtension.class) 
class QuestionServiceTest {

	@Mock
	private QuestionRepository questions;

	@InjectMocks
	private QuestionServiceImpl	questionService;

	@Test
	void addQuestion_NewQuestion_ReturnSavedQuestion() {
		//arrange
		Question question = new Question("This is a new question");
		
		when(questions.findByQuestion(any(String.class))).thenReturn(new ArrayList<Question>());
		when(questions.save(any(Question.class))).thenReturn(question);

		//act
		Question savedQuestion = questionService.addQuestion(question);

		//assert
		assertNotNull(savedQuestion);
		verify(questions).findByQuestion(question.getQuestion());
		verify(questions).save(question);
	}

	@Test
	void addQuestion_SameQuestion_ReturnNull() {
		//arrange
		Question question = new Question("This question exists");
		
		List<Question> sameQuestions = new ArrayList<Question>();
		sameQuestions.add(new Question("This question exists"));
		
		when(questions.findByQuestion(question.getQuestion())).thenReturn(sameQuestions);
		
		//act
		Question savedQuestion = questionService.addQuestion(question);

		//assert
		assertNull(savedQuestion);
		verify(questions).findByQuestion(question.getQuestion());
	}

	@Test
	void updateQuestion_NotFound_ReturnNull() {
		//arrange
		Question question = new Question("Updated Question");
		Long questionId = 10L;
		when(questions.findById(questionId)).thenReturn(Optional.empty());

		//act
		Question updatedQuestion = questionService.updateQuestion(questionId, question);

		//assert
		assertNull(updatedQuestion);
		verify(questions).findById(questionId);
	}

	// @Test
	// void updateQuestion_QuestionFound_ReturnUpdatedQuestion() {
	// 	//arrange
	// 	Long questionId = 10L;
	// 	Question question = new Question("Original Question", questionId);

	// 	Optional<Question> optQuestion = Optional.of(question);

	// 	when(questions.findById(questionId)).thenReturn(optQuestion);
	// 	System.out.println("Bob   " + questionService.updateQuestion(questionId, new Question("Updated Question")));

	// 	//act
	// 	Question updatedQuestion = questionService.updateQuestion(questionId, new Question("Updated Question"));

	// 	//assert
	// 	assertNotNull(updatedQuestion);
	// 	// verify(questions).findById(questionId);
	// }

}
