package G2T6.G2T6.G2T6.services;

import java.util.List;

import org.springframework.stereotype.Service;

import G2T6.G2T6.G2T6.models.CurrentState;
import G2T6.G2T6.G2T6.models.GameStats;
import G2T6.G2T6.G2T6.models.Question;
import G2T6.G2T6.G2T6.models.orders.OptionOrder;
import G2T6.G2T6.G2T6.models.orders.QuestionOrder;
import G2T6.G2T6.G2T6.payload.response.GameResponse;
import G2T6.G2T6.G2T6.payload.response.GameResponse2;

public interface GameService {
    //init game for user
    GameResponse initGame(CurrentState state);
    GameResponse2 initGame2(CurrentState state); //version 2 that gives all questions and options at call
    GameResponse getGameInfo(CurrentState state);
    GameResponse2 getGameInfo2(CurrentState state);
    GameStats getAnsweredStats(CurrentState currentState, int answerIdx);
    GameStats getAnsweredStats(CurrentState currentState, String[] answers);
    void nextQuestion(CurrentState currentState);
    // List<GameStats> getPastGameStatsByGameIdAndUserId(Long gameId, Long userId);
    //get optionsString for this question
    List<String> getOptionsList(Question question, OptionOrder optionOrderQ);
    void prepareNextGame(CurrentState currentState);
    GameResponse getEndGameInfo(CurrentState currentState);
}
