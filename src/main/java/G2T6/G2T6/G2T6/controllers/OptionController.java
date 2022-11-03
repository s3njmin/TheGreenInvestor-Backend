package G2T6.G2T6.G2T6.controllers;

import G2T6.G2T6.G2T6.exceptions.OptionOrderIdInvalidException;
import G2T6.G2T6.G2T6.exceptions.QuestionNotFoundException;
import G2T6.G2T6.G2T6.models.Option;
import G2T6.G2T6.G2T6.repository.OptionRepository;
import G2T6.G2T6.G2T6.repository.QuestionRepository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/questions")
@RestController
public class OptionController {
    private OptionRepository options;
    private QuestionRepository questions;

    @Autowired
    public OptionController(final OptionRepository options, final QuestionRepository questions){
        this.options = options;
        this.questions = questions;
    }

    // Returns all Options specified by Question Id
    @GetMapping("/{questionId}/options")
    public List<Option> getAllOptionsByQuestionId(@PathVariable (value = "questionId") final Long questionId) {
        // check if Question Exists
        if(!questions.existsById(questionId)) {
            throw new QuestionNotFoundException(questionId);
        }
        return options.findByQuestionId(questionId);
    }

    // Add an Option to specified Question
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{questionId}/options")
    public Option addOption(@PathVariable (value = "questionId") final Long questionId, 
            @Valid @RequestBody Option option){

        return questions.findById(questionId).map(question -> {
            option.setQuestion(question);
            return options.save(option);
        }).orElseThrow(() -> new QuestionNotFoundException(questionId));
    }

    // Update an Existing Option
    @PutMapping("/{questionId}/options/{optionId}")
    public Option updateOption(@PathVariable (value = "questionId") final Long questionId, 
            @PathVariable (value = "optionId") Long optionId,
            @Valid @RequestBody Option newOption) {
        
        // check if question exists
        if(!questions.existsById(questionId)) {
            throw new QuestionNotFoundException(questionId);
        }

        return options.findByIdAndQuestionId(optionId, questionId).map(option -> {
            option.setOption(newOption.getOption());
            return options.save(option);
        }).orElseThrow(() -> new OptionOrderIdInvalidException(optionId));
    }

    //Delete an Existing Option
    @DeleteMapping("/{questionId}/options/{optionId}")
    public ResponseEntity<?> deleteOption(@PathVariable (value = "questionId") final Long questionId,
            @PathVariable (value = "optionId") Long optionId){
        
        if(!questions.existsById(questionId)) {
            throw new QuestionNotFoundException(questionId);
        }

        return options.findByIdAndQuestionId(optionId, questionId).map(option -> {
            options.delete(option);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new OptionOrderIdInvalidException(optionId));

    }
}
