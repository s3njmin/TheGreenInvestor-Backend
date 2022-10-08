package G2T6.G2T6.G2T6.controllers;

import java.util.List;

import javax.validation.Valid;

import G2T6.G2T6.G2T6.exceptions.QuestionExistsException;
import G2T6.G2T6.G2T6.exceptions.QuestionNotFoundException;
import G2T6.G2T6.G2T6.models.Question;
import G2T6.G2T6.G2T6.services.QuestionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/questions")
    public Question addQuestion(@RequestBody Question question){
        Question savedQuestion = questionService.addQuestion(question);
        if (savedQuestion ==  null) throw new QuestionExistsException(question.getQuestion());
        return savedQuestion;
    }

    @PutMapping("/questions/{id}")
    public Question updateQuestion(@PathVariable Long id, @Valid @RequestBody Question newQuestionInfo){
        Question question = questionService.updateQuestion(id, newQuestionInfo);
        if(question == null) throw new QuestionNotFoundException(id);
        return question;
    }

    @DeleteMapping("/questions/{id}")
    public void deleteQuestion(@PathVariable Long id){
        try{
            questionService.deleteQuestion(id);
        } catch(EmptyResultDataAccessException e) {
            throw new QuestionNotFoundException(id);
        }
    }
}