package G2T6.G2T6.G2T6.payload.response;

import G2T6.G2T6.G2T6.models.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnswerResponse2 {

    // income value of game stats
    private int incomeVal = 0;

    // morale value of game stats
    private int moraleVal = 0;

    // sustainability value of game stats
    private int emissionVal = 0;

    // cost impact of game stats
    private int costVal = 0;

    // current cash in hand
    private int currentCash = 0;

    // current profitability/income/GPT
    private int currentIncome = 0;

    // current sustainability
    private int currentEmission = 0;

    // current morale
    private int currentMorale = 0;

    // multiplier
    private double multiplier = 1.0;

    // feedback
    private String feedback = "";

    // Article 
    private Article article = null;

}
