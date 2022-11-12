package G2T6.G2T6.G2T6.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerRequest2 {

    @NotBlank
    private String answer;

    public AnswerRequest2(String answer) {
        this.answer = answer;
    }

}
