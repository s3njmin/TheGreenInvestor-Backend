package G2T6.G2T6.G2T6.controllers;

import G2T6.G2T6.G2T6.exceptions.GameStatsNotFoundException;
import G2T6.G2T6.G2T6.exceptions.NotEnoughGameStatsException;
import G2T6.G2T6.G2T6.exceptions.StateNotFoundException;
import G2T6.G2T6.G2T6.exceptions.UserNotFoundException;
import G2T6.G2T6.G2T6.misc.State;
import G2T6.G2T6.G2T6.models.*;
import G2T6.G2T6.G2T6.repository.StateRepository;
import G2T6.G2T6.G2T6.models.GameStats;
import G2T6.G2T6.G2T6.models.orders.QuestionOrder;
import G2T6.G2T6.G2T6.models.security.User;
import G2T6.G2T6.G2T6.repository.GameStatsRepository;
import G2T6.G2T6.G2T6.repository.QuestionRepository;
import G2T6.G2T6.G2T6.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import G2T6.G2T6.G2T6.payload.request.AnswerRequest2;
import G2T6.G2T6.G2T6.payload.response.AnswerResponse2;
import G2T6.G2T6.G2T6.payload.response.GameResponse;
import G2T6.G2T6.G2T6.payload.response.GameResponse;
import G2T6.G2T6.G2T6.payload.response.MessageResponse;
import G2T6.G2T6.G2T6.repository.UserRepository;
import G2T6.G2T6.G2T6.models.security.User;
import org.springframework.security.core.userdetails.UserDetails;
import G2T6.G2T6.G2T6.security.AuthHelper;
import G2T6.G2T6.G2T6.services.GameService;
import G2T6.G2T6.G2T6.services.StateService;
import io.swagger.models.Response;
import G2T6.G2T6.G2T6.exceptions.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class GameController {

    @Autowired
    private GameStatsRepository gameStatsRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private QuestionRepository questionRepo;

    @GetMapping("/gameInfo")
    // this version gets all the quesetions at the start
    public ResponseEntity<?> getGameInfo() {

        try {
            UserDetails user = AuthHelper.getCurrentUser();
            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Error: User is invalid, bad authentication"));
            }

            // get the user object
            User currUser = userRepo.findByUsername(user.getUsername())
                    .orElseThrow(() -> new UserNotFoundException(user.getUsername()));

            CurrentState currentState = stateRepository.findTopByUserOrderByIdDesc(currUser)
                    .orElseThrow(() -> new StateNotFoundException(currUser.getUsername()));

            // get state of the user
            State state = currentState.getCurrentState();

            // if state is "start", which means not in game, then initialise a new game for
            // user
            if (state == State.start) {
                GameResponse gameResponse = gameService.initGame(currentState);
                return ResponseEntity.ok(gameResponse);
            }

            if (currentState.getYearValue() == 10 || currentState.getGameStats().getCurrentCashInHand() <= 0
                    || currentState.getGameStats().getCurrentMoraleVal() <= 0
                    || currentState.getGameStats().getCurrentEmissionVal() <= 0) {
                GameResponse gameResponse = gameService.getEndGameInfo(currentState);
                gameService.prepareNextGame(currentState);
                return ResponseEntity.ok(gameResponse);
            }

            // if any other states, return current info
            GameResponse gameResponse = gameService.getGameInfo(currentState);
            return ResponseEntity.ok(gameResponse);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Some other error please contact admin"));
        }

    }

    @PostMapping("/submitAnswer")
    public ResponseEntity<?> submitAnswer(@Valid @RequestBody AnswerRequest2 answerRequest) {
        try {

            UserDetails user = AuthHelper.getCurrentUser();

            if (user == null) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Error: User is invalid, bad authentication"));
            }

            // get the user object
            User currUser = userRepo.findByUsername(user.getUsername())
                    .orElseThrow(() -> new UserNotFoundException(user.getUsername()));

            // get the most recent state of the user from database by state id
            CurrentState currentState = stateRepository.findTopByUserOrderByIdDesc(currUser)
                    .orElseThrow(() -> new StateNotFoundException(currUser.getUsername()));

            if (currentState.getYearValue() == 10 || currentState.getGameStats().getCurrentCashInHand() <= 0
                    || currentState.getGameStats().getCurrentMoraleVal() <= 0
                    || currentState.getGameStats().getCurrentEmissionVal() <= 0) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Error: Game is over, please start a new game"));
            }

            QuestionOrder questionOrder = currentState.getQuestionOrder();
            int year = currentState.getYearValue(); // also can be used for question index it is currently on
            Long questionNumber = (long) questionOrder.getIndexArray().get(year);
            Question question = questionRepo.findById(questionNumber + 1)
                    .orElseThrow(() -> new QuestionNotFoundException(questionNumber));
            boolean isOpenEnded = question.isOpenEnded();
            // System.out.println("isOpenEnded: " + isOpenEnded);

            // try to parse given answer
            try {
                if (!isOpenEnded) {
                    // System.out.println("ran here 123");
                    int answer = Integer.parseInt(answerRequest.getAnswer()); // 0,1,2,3
                    // save user's answer
                    currentState.setUserResponse(answerRequest.getAnswer());
                    stateRepository.saveAndFlush(currentState);
                    // get gamestats of answered option
                    GameStats gameStats = gameService.getAnsweredStats(currentState, answer);

                    // update current vals
                    int currentCashInHand = gameStats.getCurrentCashInHand();
                    int currentIncomeVal = gameStats.getCurrentIncomeVal();
                    int currentEmissionVal = gameStats.getCurrentEmissionVal();
                    int currentMoraleVal = gameStats.getCurrentMoraleVal();

                    AnswerResponse2 answerResponse = new AnswerResponse2(gameStats.getIncomeVal(),
                            gameStats.getMoraleVal(),
                            gameStats.getEmissionVal(), gameStats.getCostVal(), currentCashInHand, currentIncomeVal,
                            currentEmissionVal, currentMoraleVal, gameStats.getMultiplier(),
                            question.getOptions().get(answer).getFeedback());

                    // Next Question
                    gameService.nextQuestion(currentState);

                    return ResponseEntity.ok(answerResponse);
                } else {
                    String[] answers = answerRequest.getAnswer().split(",");
                    // save user's answer
                    currentState.setUserResponse(answerRequest.getAnswer());
                    stateRepository.saveAndFlush(currentState);

                    // get gamestats of answered option
                    GameStats gameStats = gameService.getAnsweredStats(currentState, answers);

                    // update current vals
                    int currentCashInHand = gameStats.getCurrentCashInHand();
                    int currentIncomeVal = gameStats.getCurrentIncomeVal();
                    int currentEmissionVal = gameStats.getCurrentEmissionVal();
                    int currentMoraleVal = gameStats.getCurrentMoraleVal();

                    AnswerResponse2 answerResponse = new AnswerResponse2(gameStats.getIncomeVal(),
                            gameStats.getMoraleVal(),
                            gameStats.getEmissionVal(), gameStats.getCostVal(), currentCashInHand, currentIncomeVal,
                            currentEmissionVal, currentMoraleVal, gameStats.getMultiplier(), "null");

                    // Next Question
                    gameService.nextQuestion(currentState);

                    return ResponseEntity.ok(answerResponse);
                }

            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Error: Input is not correct"));
            }

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: An error has occurred"));

        }
    }

}
