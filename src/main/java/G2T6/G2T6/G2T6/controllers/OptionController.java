package G2T6.G2T6.G2T6.controllers;

import G2T6.G2T6.G2T6.exceptions.OptionNotFoundException;
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
@RequestMapping("/api/questions/{questionId}")
@RestController
public class OptionController {
    private OptionRepository options;
    private QuestionRepository questions;

    @Autowired
    public OptionController(final OptionRepository options, final QuestionRepository questions){
        this.options = options;
        this.questions = questions;
    }

    @GetMapping("/options")
    public List<Option> getAllOptionsByQuestionId(@PathVariable (value = "questionId") final Long questionId) {
        if(!questions.existsById(questionId)) {
            throw new QuestionNotFoundException(questionId);
        }
        return options.findByQuestionId(questionId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/options")
    public Option addOption(@PathVariable (value = "questionId") final Long questionId, 
            @Valid @RequestBody Option option){
        return questions.findById(questionId).map(question -> {
            option.setQuestion(question);
            return options.save(option);
        }).orElseThrow(() -> new QuestionNotFoundException(questionId));
    }

    @PutMapping("/options/{optionId}")
    public Option updateOption(@PathVariable (value = "questionId") final Long questionId, 
            @PathVariable (value = "optionId") Long optionId,
            @Valid @RequestBody Option newOption) {
        
        if(!questions.existsById(questionId)) {
            throw new QuestionNotFoundException(questionId);
        }
        return options.findByIdAndQuestionId(optionId, questionId).map(option -> {
            option.setOption(newOption.getOption());
            return options.save(option);
        }).orElseThrow(() -> new OptionNotFoundException(optionId));
    }

    @DeleteMapping("/options/{optionId}")
    public ResponseEntity<?> deleteOption(@PathVariable (value = "questionId") final Long questionId,
            @PathVariable (value = "optionId") Long optionId){
        
        if(!questions.existsById(questionId)) {
            throw new QuestionNotFoundException(questionId);
        }

        return options.findByIdAndQuestionId(optionId, questionId).map(option -> {
            options.delete(option);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new OptionNotFoundException(optionId));

    }
}
