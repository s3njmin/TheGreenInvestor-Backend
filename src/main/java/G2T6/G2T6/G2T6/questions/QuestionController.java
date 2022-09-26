package G2T6.G2T6.G2T6.questions;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

public class QuestionController {
    private QuestionService questionService;

    public QuestionController(QuestionService qs){
        this.questionService = qs;
    }

    @GetMapping("/questions")
    public List<Question> getQuestion() {
        return questionService.listQuestions();
    }

    @GetMapping("/questions/{id}")
    public Question getQuestion(@PathVariable Long id) {
        Question question = questionService.getQuestion(id);

        if(question == null) throw new QuestionNotFoundException(id);
        return questionService.getQuestion(id);
    }

    
}