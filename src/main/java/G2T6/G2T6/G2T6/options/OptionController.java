package G2T6.G2T6.G2T6.options;

import java.util.List;
import G2T6.G2T6.G2T6.questions.*;

import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/questions/{questionId}")
@RestController
public class OptionController {
    private OptionRepository options;
    private QuestionRepository questions;

    public OptionController(OptionRepository options, QuestionRepository questions){
        this.options = options;
        this.questions = questions;
    }

    @GetMapping("/options")
    public List<Option> getAllOptionsByQuestionId(@PathVariable (value = "questionId") Long questionId) {
        if(!questions.existsById(questionId)) {
            throw new QuestionNotFoundException(questionId);
        }
        return options.findByQuestionId(questionId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/options")
    public Option addOption(@PathVariable (value = "questionId") Long questionId, 
            @Valid @RequestBody Option option){
        return questions.findById(questionId).map(question -> {
            option.setQuestion(question);
            return options.save(option);
        }).orElseThrow(() -> new QuestionNotFoundException(questionId));
    }

    @PutMapping("/options/{optionId}")
    public Option updateOption(@PathVariable (value = "questionId") Long questionId, 
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
    public ResponseEntity<?> deleteOption(@PathVariable (value = "questionId") Long questionId,
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
