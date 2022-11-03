package G2T6.G2T6.G2T6.controllers;

import java.util.*;

import javax.validation.Valid;

import G2T6.G2T6.G2T6.exceptions.QuestionExistsException;
import G2T6.G2T6.G2T6.exceptions.QuestionNotFoundException;
import G2T6.G2T6.G2T6.models.Option;
import G2T6.G2T6.G2T6.models.Question;
import G2T6.G2T6.G2T6.services.QuestionService;

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
    public QuestionController(final QuestionService qs){
        this.questionService = qs;
    }

    // return all questions & options
    @GetMapping("/questionsAndOptions")
    public List<Question> getQuestionAndOptions() {

        // randomly ordered indexes (0 to 9)
        ArrayList<Integer> questionIndexes = new ArrayList<>();
        Collections.addAll(questionIndexes, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(questionIndexes);
        
        // store 
        List<Question> questions = questionService.listQuestions();
        List<Question> randomizedQuestions = new ArrayList<>();

        for (int idx : questionIndexes) {
            Question question = questions.get(idx);
            List<Option> options = question.getOptions();
            
            // if NOT open ended question, randomly remove 2 options
            if (!question.isOpenEnded()) {
                Random random = new Random();
                options.remove(random.nextInt(6));
                options.remove(random.nextInt(5));
            }

            // set list of options after removing 2 options randomly
            question.setOptions(options);

            // add question to randomized questions
            randomizedQuestions.add(question);
        }

        return randomizedQuestions;
    }

    // return all questions & options
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