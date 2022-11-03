package G2T6.G2T6.G2T6.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import G2T6.G2T6.G2T6.exceptions.QuestionNotFoundException;
import G2T6.G2T6.G2T6.models.CurrentState;
import G2T6.G2T6.G2T6.models.GameStats;
import G2T6.G2T6.G2T6.models.Option;
import G2T6.G2T6.G2T6.models.Question;
import G2T6.G2T6.G2T6.models.orders.OptionOrder;
import G2T6.G2T6.G2T6.models.orders.QuestionOrder;
import G2T6.G2T6.G2T6.payload.response.GameResponse;
import G2T6.G2T6.G2T6.repository.GameStatsRepository;
import G2T6.G2T6.G2T6.repository.OptionRepository;
import G2T6.G2T6.G2T6.repository.QuestionOrderRepository;
import G2T6.G2T6.G2T6.repository.QuestionRepository;
import G2T6.G2T6.G2T6.repository.StateRepository;
import G2T6.G2T6.G2T6.services.similarity.LevenshteinDistanceStrategy;
import G2T6.G2T6.G2T6.services.similarity.StringSimilarityServiceImpl;
import G2T6.G2T6.G2T6.misc.*;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private StateRepository stateRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private GameStatsRepository gameStatsRepo;

    @Autowired
    private QuestionOrderRepository questionOrderRepo;

    @Autowired
    private OptionRepository optionRepo;

    @Autowired
    private StateService stateService;

    @Override
    public GameResponse initGame(CurrentState currentState) {

        QuestionOrder newOrder = new QuestionOrder();
        currentState.setQuestionOrder(newOrder);
        // change to answering because user has started the game
        currentState.setCurrentState(State.answering);
        ArrayList<CurrentState> states = new ArrayList<CurrentState>();
        states.add(currentState);
        newOrder.setCurrentStates(states);

        // save the new order
        questionOrderRepo.saveAndFlush(newOrder);

        // right now it reproduce same option list NOTE
        // System.out.println(newOrder.getIndexArray());
        // for (int i = 0; i < newOrder.getOptionOrders().size(); i++) {
        // System.out.println(newOrder.getOptionOrders().get(0).getIndexArray());
        // }

        stateRepo.saveAndFlush(currentState);

        QuestionOrder questionOrder = currentState.getQuestionOrder();

        Long questionNumber = (long) questionOrder.getIndexArray().get(0);

        Question question = questionRepo.findById(questionNumber + 1)
                .orElseThrow(() -> new QuestionNotFoundException(questionNumber));

        String questionName = question.getQuestion();
        List<String> optionsName = getOptionsList(question, questionOrder.getOptionOrders().get(0));
        boolean isOpenEnded = question.isOpenEnded();
        int year = currentState.getYearValue();

        // get past game stats
        List<CurrentState> pastState = stateRepo.findByGameIdAndUserId(currentState.getGameId(),
                currentState.getUser().getId());
        List<GameStats> pastGameStats = new ArrayList<GameStats>();
        // iterate through pastState if it exists
        if (pastState != null) {
            for (CurrentState state : pastState) {
                pastGameStats.add(gameStatsRepo.findByCurrentState(state));
            }
        }

        if (question.isOpenEnded())
            optionsName = null;

        GameResponse gameResponse = new GameResponse(State.start, questionName, optionsName, isOpenEnded, year,
                pastGameStats, 1.0);

        return gameResponse;
    }

    @Override
    public GameResponse getGameInfo(CurrentState currentState) {
        QuestionOrder questionOrder = currentState.getQuestionOrder();
        int year = currentState.getYearValue(); // also can be used for question index it is currently on

        Long questionNumber = (long) questionOrder.getIndexArray().get(year);

        Question question = questionRepo.findById(questionNumber + 1)
                .orElseThrow(() -> new QuestionNotFoundException(questionNumber));

        String questionName = question.getQuestion();
        List<String> optionsName = getOptionsList(question, questionOrder.getOptionOrders().get(year));
        boolean isOpenEnded = question.isOpenEnded();

        // get past game stats
        List<CurrentState> pastState = stateRepo.findByGameIdAndUserId(currentState.getGameId(),
                currentState.getUser().getId());
        List<GameStats> pastGameStats = new ArrayList<GameStats>();
        // iterate through pastState if it exists
        if (pastState != null) {
            for (CurrentState state : pastState) {
                pastGameStats.add(gameStatsRepo.findByCurrentState(state));
            }
        }

        if (question.isOpenEnded())
            optionsName = null;

        GameResponse gameResponse = new GameResponse(currentState.getCurrentState(), questionName, optionsName,
                isOpenEnded, year, pastGameStats,
                currentState.getGameStats() == null ? 1.0 : currentState.getGameStats().getMultiplier());

        return gameResponse;
    }

    @Override
    public GameStats getAnsweredStats(CurrentState currentState, int answerIdx) {

        QuestionOrder questionOrder = currentState.getQuestionOrder();
        int year = currentState.getYearValue(); // also can be used for question index it is currently on

        Long questionNumber = (long) questionOrder.getIndexArray().get(year);

        Question question = questionRepo.findById(questionNumber + 1)
                .orElseThrow(() -> new QuestionNotFoundException(questionNumber));

        Option option = question.getOptions()
                .get(questionOrder.getOptionOrders().get(year).getIndexArray().get(answerIdx));

        int sustainabilityImpact = option.getSustainabilityImpact();
        int moraleImpact = option.getMoraleImpact();
        int incomeImpact = option.getIncomeImpact();
        int costImpact = option.getCostImpact();

        GameStats gameStats = new GameStats(sustainabilityImpact, moraleImpact, incomeImpact, costImpact,
                currentState.getUser(), currentState,
                currentState.getGameStats() == null ? 1.0 : currentState.getGameStats().getMultiplier());

        gameStatsRepo.saveAndFlush(gameStats);

        return gameStats;
    }

    @Override
    public GameStats getAnsweredStats(CurrentState currentState, String[] answers) {

        System.out.println("ran here 1");

        QuestionOrder questionOrder = currentState.getQuestionOrder();
        int year = currentState.getYearValue(); // also can be used for question index it is currently on

        Long questionNumber = (long) questionOrder.getIndexArray().get(year);

        List<Option> answerList = optionRepo.findByQuestionId(questionNumber + 1);

        // loop through all options and get score with highest
        double highestScore = 0;
        double averageScore = 0; // over three answers so divide by 3

        StringSimilarityServiceImpl stringSimilarityServiceImpl = new StringSimilarityServiceImpl(
                new LevenshteinDistanceStrategy());

        // loop through answer array
        for (String answerString : answers) {
            System.out.println("ran here 2");
            // System.out.println(answerString);
            for (Option option : answerList) {
                double score = stringSimilarityServiceImpl.score(answerString, option.getOption());
                // System.out.println("Comparing " + answerString + " and " + option.getOption()
                // + " score: " + score);
                if (score > highestScore) {
                    highestScore = score;
                }
            }
            averageScore += highestScore / 3.0;
            highestScore = 0;
            System.out.println("average score: " + averageScore);
        }

        System.out.println("ran here 3");

        GameStats gameStats = new GameStats(0, 0, 0, 0,
                currentState.getUser(), currentState, averageScore);

        gameStatsRepo.saveAndFlush(gameStats);

        return gameStats;
    }

    @Override
    public void nextQuestion(CurrentState currentState) {

        // QuestionOrder newOrder = new
        // QuestionOrder(currentState.getQuestionOrder().getIndexArray(),
        // currentState.getQuestionOrder().getOptionOrders());

        int year = currentState.getYearValue(); // also can be used for question index it is currently on
        int nextYear = year + 1;

        CurrentState newState = new CurrentState(currentState.getGameId(), currentState.getUser(), nextYear,
                currentState.getCurrentState(), currentState.getQuestionOrder());
        // newOrder.setCurrentState(newState);
        // newState.setYearValue(nextYear);
        stateRepo.saveAndFlush(newState);

    }

    @Override
    public void prepareNextGame(CurrentState currentState) {

        CurrentState newState = stateService.getDefaultState();

        Long currGameId = currentState.getGameId();
        Long nextGameId = currGameId + 1;

        //set gameId to next game
        newState.setGameId(nextGameId);
        newState.setUser(currentState.getUser());

        QuestionOrder newOrder = new QuestionOrder();
        newState.setQuestionOrder(newOrder);

        ArrayList<CurrentState> states = new ArrayList<CurrentState>();
        states.add(newState);
        newOrder.setCurrentStates(states);

        // save the new order
        questionOrderRepo.saveAndFlush(newOrder);


        stateRepo.saveAndFlush(newState);
    }

    @Override
    public List<String> getOptionsList(Question question, OptionOrder optionOrder) {

        ArrayList<String> optionsName = new ArrayList<String>();

        for (int i = 0; i < 4; i++) {
            optionsName.add(question.getOptions().get(optionOrder.getIndexArray().get(i)).getOption());
        }

        return optionsName;

    }

}
