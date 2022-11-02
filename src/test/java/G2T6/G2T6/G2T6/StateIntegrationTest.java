package G2T6.G2T6.G2T6;

import G2T6.G2T6.G2T6.misc.CONSTANTVARIABLES;
import G2T6.G2T6.G2T6.models.CurrentState;
import G2T6.G2T6.G2T6.models.security.User;
import G2T6.G2T6.G2T6.models.GameStats;
import G2T6.G2T6.G2T6.repository.GameStatsRepository;
import G2T6.G2T6.G2T6.repository.RefreshTokenRepository;
import G2T6.G2T6.G2T6.repository.StateRepository;
import G2T6.G2T6.G2T6.repository.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.URI;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StateIntegrationTest {
    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StateRepository stateRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private User adminUser;
    private User regularUser;

    @Autowired
    private GameStatsRepository gameStatsRepo;

    @Autowired
    private RefreshTokenRepository refreshRepo;

    @AfterEach
    void tearDown() {
        // clear the database after each test
        stateRepo.deleteAll();
        gameStatsRepo.deleteAll();
        refreshRepo.deleteAll();
        userRepo.deleteAll();
    }

     @Test
     public void getState_Success() throws Exception {
         URI uri = new URI(baseUrl + port + "/api/states");
         stateRepo.save(new CurrentState(1L, CONSTANTVARIABLES.DEFAULTYEAR, CONSTANTVARIABLES.DEFAULTSTATE));

         ResponseEntity<CurrentState[]> result = restTemplate.getForEntity(uri, CurrentState[].class);
         CurrentState[] states = result.getBody();

         assertEquals(200, result.getStatusCode().value());
         assertEquals(1, states.length);
     }

    // @Test
    // public void addState_Success() throws Exception {
    // URI uri = new URI(baseUrl + port + "/api/id");
    // CurrentState state = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR,
    // CONSTANTVARIABLES.DEFAULTSTATE);
    // ResponseEntity<CurrentState> result = restTemplate.postForEntity(uri, state,
    // CurrentState.class);

    // assertEquals(201, result.getStatusCode().value());
    // assertEquals(state.getCurrentState(), result.getBody().getCurrentState());
    // assertEquals(state.getYearValue(), result.getBody().getYearValue());
    // }

    // @Test
    // public void deleteState_ValidId_Success() throws Exception{
    // CurrentState state = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR,
    // CONSTANTVARIABLES.DEFAULTSTATE);
    // URI uri = new URI(baseUrl + port + "/api/" + state.getId().longValue());
    // ResponseEntity<Void> result = restTemplate.exchange(uri, HttpMethod.DELETE,
    // null, Void.class);

    // assertEquals(200, result.getStatusCode().value());
    // Optional<CurrentState> emptyValue = Optional.empty();
    // assertEquals(emptyValue, stateRepo.findById(state.getId()));
    // }

    @Test
    public void deleteState_ValidId_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/id/1");
        ResponseEntity<Void> result = restTemplate.exchange(uri, HttpMethod.DELETE, null, Void.class);
        assertEquals(404, result.getStatusCode().value());
    }

    // @Test
    // public void updateState_ValidId_Success() throws Exception {
    // CurrentState state = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR,
    // CONSTANTVARIABLES.DEFAULTSTATE);
    // URI uri = new URI(baseUrl + port + "/api/" + state.getId().longValue());
    // CurrentState newState = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR,
    // CONSTANTVARIABLES.DEFAULTSTATE);
    // ResponseEntity<CurrentState> result = restTemplate.exchange(uri,
    // HttpMethod.PUT, new HttpEntity<>(newState), CurrentState.class);
    // assertEquals(200, result.getStatusCode().value());
    // assertEquals(state.getCurrentState(), result.getBody().getCurrentState());
    // assertEquals(state.getYearValue(), result.getBody().getYearValue());
    // }
    // @Test
    // public void updateState_ValidId_Failure() throws Exception {
    // URI uri = new URI(baseUrl + port + "/api/id/1");
    // CurrentState newState = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR,
    // CONSTANTVARIABLES.DEFAULTSTATE);
    // ResponseEntity<CurrentState> result = restTemplate.exchange(uri,
    // HttpMethod.PUT, new HttpEntity<>(newState), CurrentState.class);
    // assertEquals(200, result.getStatusCode().value());
    // assertEquals(newState.getCurrentState(), result.getBody().getCurrentState());
    // assertEquals(newState.getYearValue(), result.getBody().getYearValue());
    // }

    @Test
    public void getState_ValidUserAndId_Success() throws Exception {

        User regularUser = new User("ckasdasd123", "ck123@gmail.com", encoder.encode("Password1232"), "GUEST");

        GameStats gamestat = new GameStats();

        CurrentState currentState = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR, CONSTANTVARIABLES.DEFAULTSTATE);

        currentState.setGameStats(gamestat);
        gamestat.setCurrentState(currentState);

        List<GameStats> gameStatsList = new ArrayList<>();
        List<CurrentState> currentStates = new ArrayList<>();

        gamestat.setUser(regularUser);
        currentState.setUser(regularUser);

        gameStatsList.add(gamestat);
        currentStates.add(currentState);

        regularUser.setGameStats(gameStatsList);
        regularUser.setCurrentState(currentStates);

        //save user to database
        Long userId = userRepo.save(regularUser).getId();
        Long currentStateId = stateRepo.save(currentState).getId();
        gameStatsRepo.save(gamestat);
        // stateRepo.save(currentState);
        

        URI uri = new URI(baseUrl + port + "/api/id/" + userId + "/states/" + currentStateId);

        ResponseEntity<CurrentState> result = restTemplate.getForEntity(uri, CurrentState.class);

        System.out.println(result.getBody().getCurrentState());
        System.out.println(result.getHeaders());
        System.out.println(result.getStatusCode().value());

        assertEquals(200, result.getStatusCode().value());
        assertEquals(currentState.getCurrentState(), result.getBody().getCurrentState());
        assertEquals(currentState.getYearValue(), result.getBody().getYearValue());
    }
}
