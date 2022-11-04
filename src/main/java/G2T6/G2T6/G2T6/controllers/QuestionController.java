package G2T6.G2T6.G2T6.controllers;

import java.util.*;

import javax.validation.Valid;

import G2T6.G2T6.G2T6.exceptions.OptionOrderIdInvalidException;
import G2T6.G2T6.G2T6.exceptions.QuestionExistsException;
import G2T6.G2T6.G2T6.exceptions.QuestionNotFoundException;
import G2T6.G2T6.G2T6.exceptions.QuestionOrderIdInvalidException;
import G2T6.G2T6.G2T6.models.Option;
import G2T6.G2T6.G2T6.models.Question;
import G2T6.G2T6.G2T6.models.orders.OptionOrder;
import G2T6.G2T6.G2T6.models.orders.QuestionOrder;
import G2T6.G2T6.G2T6.repository.OptionOrderRepository;
import G2T6.G2T6.G2T6.repository.QuestionOrderRepository;
import G2T6.G2T6.G2T6.services.QuestionService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class QuestionController {
    private QuestionService questionService;
    
    @Autowired
    public QuestionController(final QuestionService questionService){
        this.questionService = questionService;
    }

    // return all questions & options (called from frontend & start of game)
    @GetMapping("/questionsAndOptions")
    public List<Question> getQuestionAndOptions() {
        return questionService.randomizedQuestions();
    }

    // return all questions
    @GetMapping("/questions")
    public List<Question> getQuestion() {
        return questionService.listQuestions();
    }

    @GetMapping("/questions/{id}")
    public Question getQuestion(@PathVariable final Long id) {
        Question question = questionService.getQuestion(id);
        if(question == null) throw new QuestionNotFoundException(id);
        return questionService.getQuestion(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/questions")
    public Question addQuestion(@RequestBody final Question question){
        Question savedQuestion = questionService.addQuestion(question);
        if (savedQuestion ==  null) throw new QuestionExistsException(question.getQuestion());
        return savedQuestion;
    }

    @PutMapping("/questions/{id}")
    public Question updateQuestion(@PathVariable final Long id, @Valid @RequestBody final Question newQuestionInfo){
        Question question = questionService.updateQuestion(id, newQuestionInfo);
        if(question == null) throw new QuestionNotFoundException(id);
        return question;
    }

    @DeleteMapping("/questions/{id}")
    public void deleteQuestion(@PathVariable final Long id){
        try{
            questionService.deleteQuestion(id);
        } catch(EmptyResultDataAccessException e) {
            throw new QuestionNotFoundException(id);
        }
    }
}