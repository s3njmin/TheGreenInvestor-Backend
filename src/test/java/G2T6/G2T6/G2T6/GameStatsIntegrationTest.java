package G2T6.G2T6.G2T6;

import G2T6.G2T6.G2T6.misc.CONSTANTVARIABLES;
import G2T6.G2T6.G2T6.misc.State;
import G2T6.G2T6.G2T6.models.*;
import G2T6.G2T6.G2T6.models.security.User;
import G2T6.G2T6.G2T6.repository.GameStatsRepository;
import G2T6.G2T6.G2T6.repository.StateRepository;
import G2T6.G2T6.G2T6.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameStatsIntegrationTest {
    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StateRepository stateRepo;

    @Autowired
    private GameStatsRepository gameStatsRepo;

    @Autowired
    private UserRepository userRepo;

    private User regularUser;


    @AfterEach
    void tearDown() {
        // clear the database after each test
        stateRepo.deleteAll();
        userRepo.deleteAll();
        gameStatsRepo.deleteAll();
    }
    @BeforeEach
    void setUp(){
        regularUser = userRepo.save(new User("ckasdasd123", "ck123@gmail.com", "Password1232", "GUEST"));
    }

    // get
    //     @GetMapping("/gameStats")
    @Test
    public void getAllGameStats_NoData_Success() throws Exception{
        URI uri = new URI(baseUrl + port + "/api/gameStats");
        ResponseEntity<GameStats[]> result = restTemplate.getForEntity(uri, GameStats[].class);
        GameStats[] gs = result.getBody();
        assertEquals(200, result.getStatusCode().value());
        assertEquals(0, gs.length);
    }

    @Test
    public void getAllGameStats_WithData_Success() throws Exception{
        URI uri = new URI(baseUrl + port + "/api/gameStats");
        GameStats gs1 =  gameStatsRepo.save(new GameStats(10,10,10, regularUser, null));
        GameStats gs2 = gameStatsRepo.save(new GameStats(0,0,0, regularUser, null));
        ResponseEntity<GameStats[]> result = restTemplate.getForEntity(uri, GameStats[].class);
        GameStats[] gs = result.getBody();
        assertEquals(200, result.getStatusCode().value());
        assertEquals(2, gs.length);
        assertEquals(gs1.getEmissionVal(), gs[0].getEmissionVal());
        assertEquals(gs2.getEmissionVal() , gs[1].getEmissionVal());
    }

    //     @GetMapping("/id/{userId}/gameStats/{id}")
    @Test
    public void getGameStats_ValidUserIdAndId_Success() throws Exception{
        GameStats gs1 =  gameStatsRepo.save(new GameStats(10,10,10, regularUser, null));
        URI uri = new URI(baseUrl + port + "/api/id/" + regularUser.getId() +"/gameStats/" + gs1.getId());
        ResponseEntity<GameStats> result = restTemplate.getForEntity(uri, GameStats.class);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(gs1.getEmissionVal(), result.getBody().getEmissionVal());
        assertEquals(gs1.getIncomeVal(), result.getBody().getIncomeVal());
        assertEquals(gs1.getMoraleVal(), result.getBody().getMoraleVal());

    }
    @Test
    public void getGameStats_ValidUserIdAndInvalidId_Success() throws Exception{
        GameStats gs1 =  gameStatsRepo.save(new GameStats(10,10,10, regularUser, null));
        URI uri = new URI(baseUrl + port + "/api/id/" + regularUser.getId() +"/gameStats/" + gs1.getId() + 100);
        ResponseEntity<GameStats> result = restTemplate.getForEntity(uri, GameStats.class);
        assertEquals(200, result.getStatusCode().value());
        assertNull(result.getBody());
    }
    @Test
    public void getGameStats_InvalidUserIdAndId_Failure() throws Exception{
        GameStats gs1 =  gameStatsRepo.save(new GameStats(10,10,10, regularUser, null));
        URI uri = new URI(baseUrl + port + "/api/id/" + regularUser.getId() + 100 +"/gameStats/" + gs1.getId());
        ResponseEntity<GameStats> result = restTemplate.getForEntity(uri, GameStats.class);
        assertEquals(404, result.getStatusCode().value());
    }

    //     @GetMapping("/id/{userId}/gameStats")
    @Test
    public void getAllSelectedUserGameStats_ValidUserIdWithData_Success() throws Exception{
        GameStats gs1 =  gameStatsRepo.save(new GameStats(10,10,10, regularUser, null));
        GameStats gs2 =  gameStatsRepo.save(new GameStats(1,1,1, regularUser, null));
        List<GameStats> gs = new ArrayList<>();
        gs.add(gs1);
        gs.add(gs2);
        regularUser.setGameStats(gs);
        URI uri = new URI(baseUrl + port + "/api/id/" + regularUser.getId() +"/gameStats");

        ResponseEntity<GameStats[]> result = restTemplate.getForEntity(uri, GameStats[].class);
        assertEquals(200, result.getStatusCode().value());
        GameStats[] gsResult = result.getBody();
        assertEquals(2, gsResult.length);
        assertEquals(gs1.getEmissionVal(), gsResult[0].getEmissionVal());
        assertEquals(gs2.getEmissionVal(), gsResult[1].getEmissionVal());
    }

    @Test
    public void getAllSelectedUserGameStats_ValidUserIdWithoutData_Success() throws Exception{
        List<GameStats> gs = new ArrayList<>();
        regularUser.setGameStats(gs);
        URI uri = new URI(baseUrl + port + "/api/id/" + regularUser.getId() +"/gameStats");

        ResponseEntity<GameStats[]> result = restTemplate.getForEntity(uri, GameStats[].class);
        assertEquals(200, result.getStatusCode().value());
        GameStats[] gsResult = result.getBody();
        assertEquals(0, gsResult.length);
    }

    @Test
    public void getAllSelectedUserGameStats_InvalidUserId_Success() throws Exception{
        GameStats gs1 =  gameStatsRepo.save(new GameStats(10,10,10, regularUser, null));
        GameStats gs2 =  gameStatsRepo.save(new GameStats(1,1,1, regularUser, null));
        List<GameStats> gs = new ArrayList<>();
        gs.add(gs1);
        gs.add(gs2);
        regularUser.setGameStats(gs);
        URI uri = new URI(baseUrl + port + "/api/id/" + regularUser.getId() + 100 +"/gameStats");

        ResponseEntity<GameStats> result = restTemplate.getForEntity(uri, GameStats.class);
        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    public void getAllTopNGameStats_EnoughCount_Success() throws Exception{
        CurrentState cs0101 = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR, State.completed, regularUser);
        CurrentState cs0102 = new CurrentState(10, State.completed, regularUser);

        List<CurrentState> csList01 = new ArrayList<>();
        csList01.add(cs0101);
        csList01.add(cs0102);

        GameStats gs1 =  new GameStats(10,10,10, regularUser, cs0101);
        GameStats gs2 =  new GameStats(1,1,1, regularUser, cs0102);
        List<GameStats> gsList01 = new ArrayList<>();
        gsList01.add(gs1);
        gsList01.add(gs2);

        regularUser.setCurrentState(csList01);
        regularUser.setGameStats(gsList01);
        stateRepo.save(cs0101);
        stateRepo.save(cs0102);
        gameStatsRepo.save(gs1);
        gameStatsRepo.save(gs2);

        User user02 = userRepo.save(new User("fasfajisoaisf", "fasfajisoaisf@gmail.com", "PASSWORD123", "GUEST"));

        CurrentState cs0201 = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR, State.completed, user02);
        CurrentState cs0202 = new CurrentState(10, State.completed, user02);

        List<CurrentState> csList02 = new ArrayList<>();
        csList01.add(cs0201);
        csList02.add(cs0202);

        GameStats gs3 =  new GameStats(10,9,10, user02, cs0201);
        GameStats gs4 =  new GameStats(1,2,1, user02, cs0202);
        List<GameStats> gsList02 = new ArrayList<>();
        gsList02.add(gs3);
        gsList02.add(gs4);

        user02.setCurrentState(csList02);
        user02.setGameStats(gsList02);
        stateRepo.save(cs0201);
        stateRepo.save(cs0202);
        gameStatsRepo.save(gs3);
        gameStatsRepo.save(gs4);

        URI uri = new URI(baseUrl + port + "/api/gameStats/2");
        ResponseEntity<GameStats[]> result = restTemplate.getForEntity(uri, GameStats[].class);
        GameStats[] gsArray = result.getBody();
        assertEquals(200, result.getStatusCode().value());
        assertEquals(gs1.getTotal(), gsArray[0].getTotal());
        assertEquals(gs3.getTotal(), gsArray[1].getTotal());
    }

    @Test
    public void getAllTopNGameStats_NotEnoughCount_Failure() throws Exception{
        CurrentState cs0101 = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR, State.completed, regularUser);
        CurrentState cs0102 = new CurrentState(10, State.completed, regularUser);

        List<CurrentState> csList01 = new ArrayList<>();
        csList01.add(cs0101);
        csList01.add(cs0102);

        GameStats gs1 =  new GameStats(10,10,10, regularUser, cs0101);
        GameStats gs2 =  new GameStats(1,1,1, regularUser, cs0102);
        List<GameStats> gsList01 = new ArrayList<>();
        gsList01.add(gs1);
        gsList01.add(gs2);

        regularUser.setCurrentState(csList01);
        regularUser.setGameStats(gsList01);
        stateRepo.save(cs0101);
        stateRepo.save(cs0102);
        gameStatsRepo.save(gs1);
        gameStatsRepo.save(gs2);

        User user02 = userRepo.save(new User("fasfajisoaisf", "fasfajisoaisf@gmail.com", "PASSWORD123", "GUEST"));

        CurrentState cs0201 = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR, State.completed, user02);
        CurrentState cs0202 = new CurrentState(10, State.completed, user02);

        List<CurrentState> csList02 = new ArrayList<>();
        csList01.add(cs0201);
        csList02.add(cs0202);

        GameStats gs3 =  new GameStats(10,9,10, user02, cs0201);
        GameStats gs4 =  new GameStats(1,2,1, user02, cs0202);
        List<GameStats> gsList02 = new ArrayList<>();
        gsList02.add(gs3);
        gsList02.add(gs4);

        user02.setCurrentState(csList02);
        user02.setGameStats(gsList02);
        stateRepo.save(cs0201);
        stateRepo.save(cs0202);
        gameStatsRepo.save(gs3);
        gameStatsRepo.save(gs4);

        URI uri = new URI(baseUrl + port + "/api/gameStats/3");
        ResponseEntity<GameStats> result = restTemplate.getForEntity(uri, GameStats.class);

        assertEquals(404, result.getStatusCode().value());
    }

    // add
    @Test
    public void addGameStats_ValidUserId_Success() throws Exception{
        GameStats gameStats = new GameStats(1,3,3,regularUser, null);
        List<GameStats> gs = new ArrayList<>();
        gs.add(gameStats);

        regularUser.setGameStats(gs);

        URI uri = new URI(baseUrl + port + "/api/id/" + regularUser.getId() +"/gameStats");
        ResponseEntity<GameStats> result = restTemplate.getRestTemplate().postForEntity(uri, gameStats, GameStats.class);
        assertEquals(201, result.getStatusCode().value());
        assertEquals(gameStats.getIncomeVal(), result.getBody().getIncomeVal());
        assertEquals(gameStats.getEmissionVal(), result.getBody().getEmissionVal());
        assertEquals(gameStats.getMoraleVal(), result.getBody().getMoraleVal());
    }

    @Test
    public void addGameStats_InvalidUserId_Success() throws Exception{
        GameStats gameStats = new GameStats(1,3,3,regularUser, null);
        List<GameStats> gs = new ArrayList<>();
        gs.add(gameStats);

        regularUser.setGameStats(gs);

        URI uri = new URI(baseUrl + port + "/api/id/" + regularUser.getId()  + 100 +"/gameStats");
        ResponseEntity<GameStats> result = restTemplate.getRestTemplate().postForEntity(uri, gameStats, GameStats.class);
        assertEquals(404, result.getStatusCode().value());
    }



    // update
    @Test
    public void updateGameStats_ValidId_Success() throws Exception{
        GameStats gameStats = gameStatsRepo.save(new GameStats(1,3,3,regularUser, null));
        URI uri = new URI(baseUrl + port + "/api/gameStats/" + gameStats.getId());

        GameStats newReplacingGameStats = new GameStats(10,5,0,regularUser, null);
        ResponseEntity<GameStats> result = restTemplate.getRestTemplate().exchange(uri, HttpMethod.PUT,  new HttpEntity<>(newReplacingGameStats), GameStats.class);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(newReplacingGameStats.getIncomeVal(), result.getBody().getIncomeVal());
        assertEquals(newReplacingGameStats.getEmissionVal(), result.getBody().getEmissionVal());
        assertEquals(newReplacingGameStats.getMoraleVal(), result.getBody().getMoraleVal());
    }

    @Test
    public void updateGameStats_InvalidId_Failure() throws Exception{
        GameStats gameStats = gameStatsRepo.save(new GameStats(1,3,3,regularUser, null));
        URI uri = new URI(baseUrl + port + "/api/gameStats/" + gameStats.getId() + 100);

        GameStats newReplacingGameStats = new GameStats(10,5,0,regularUser, null);
        ResponseEntity<GameStats> result = restTemplate.getRestTemplate().exchange(uri, HttpMethod.PUT,  new HttpEntity<>(newReplacingGameStats), GameStats.class);
        assertEquals(404, result.getStatusCode().value());
    }

    // delete
    @Test
    public void deleteGameStates_ValidId_Failure() throws Exception{
        GameStats gameStats = gameStatsRepo.save(new GameStats(1,3,3,regularUser, null));
        URI uri = new URI(baseUrl + port + "/api/gameStats/" + gameStats.getId());
        ResponseEntity<Void> result = restTemplate.getRestTemplate().exchange(uri, HttpMethod.DELETE, null, Void.class);
        assertEquals(200, result.getStatusCode().value());
        Optional<GameStats> emptyValue = Optional.empty();
        assertEquals(emptyValue, gameStatsRepo.findById(gameStats.getId()));
    }
    @Test
    public void deleteGameStates_InvalidId_Failure() throws Exception{
        URI uri = new URI(baseUrl + port + "/api/gameStats/1");
        ResponseEntity<Void> result = restTemplate.getRestTemplate().exchange(uri, HttpMethod.DELETE, null, Void.class);
        assertEquals(404, result.getStatusCode().value());
    }
}
