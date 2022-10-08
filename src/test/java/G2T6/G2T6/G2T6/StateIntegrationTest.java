package G2T6.G2T6.G2T6;

import G2T6.G2T6.G2T6.StateMachine.PlayerCurrentState;
import G2T6.G2T6.G2T6.StateMachine.StateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.awt.print.Book;
import java.net.URI;
import java.util.Optional;

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

    @AfterEach
    void tearDown(){
        // clear the database after each test
        stateRepo.deleteAll();
    }

    @Test
    public void getState_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/id");
        stateRepo.save(new PlayerCurrentState(CONSTANTVARIABLES.DEFAULTYEAR, CONSTANTVARIABLES.DEFAULTSTATE));

        ResponseEntity<PlayerCurrentState[]> result = restTemplate.getForEntity(uri, PlayerCurrentState[].class);
        PlayerCurrentState[] states = result.getBody();

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, states.length);
    }

    // @Test
    // public void getState_ValidId_Success() throws Exception {
    //     PlayerCurrentState state = new PlayerCurrentState(CONSTANTVARIABLES.DEFAULTYEAR, CONSTANTVARIABLES.DEFAULTSTATE);
    //     Long id = state.getId();

    //     URI uri = new URI(baseUrl + port + "/api/id/" + id);
    //     ResponseEntity<PlayerCurrentState> result = restTemplate.getForEntity(uri, PlayerCurrentState.class);
    //     assertEquals(200, result.getStatusCode().value());
    //     assertEquals(state.getCurrentState(), result.getBody().getCurrentState());
    //     assertEquals(state.getYearValue(), result.getBody().getYearValue());
    // }

    @Test
    public void getState_InvalidId_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/id/1");
        ResponseEntity<PlayerCurrentState> result = restTemplate.getForEntity(uri, PlayerCurrentState.class);
        assertEquals(404, result.getStatusCode().value());
    }

    @Test
    public void addState_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/id");
        PlayerCurrentState state = new PlayerCurrentState(CONSTANTVARIABLES.DEFAULTYEAR, CONSTANTVARIABLES.DEFAULTSTATE);
        ResponseEntity<PlayerCurrentState> result = restTemplate.postForEntity(uri, state, PlayerCurrentState.class);

        assertEquals(201, result.getStatusCode().value());
        assertEquals(state.getCurrentState(), result.getBody().getCurrentState());
        assertEquals(state.getYearValue(), result.getBody().getYearValue());
    }

    // @Test
    // public void deleteState_ValidId_Success() throws Exception{
    //     PlayerCurrentState state = new PlayerCurrentState(CONSTANTVARIABLES.DEFAULTYEAR, CONSTANTVARIABLES.DEFAULTSTATE);
    //     URI uri = new URI(baseUrl + port + "/api/" + state.getId().longValue());
    //     ResponseEntity<Void> result = restTemplate.exchange(uri, HttpMethod.DELETE, null, Void.class);

    //     assertEquals(200, result.getStatusCode().value());
    //     Optional<PlayerCurrentState> emptyValue = Optional.empty();
    //     assertEquals(emptyValue, stateRepo.findById(state.getId()));
    // }

    @Test
    public void deleteState_ValidId_Failure() throws Exception{
        URI uri = new URI(baseUrl + port + "/api/id/1");
        ResponseEntity<Void> result = restTemplate.exchange(uri, HttpMethod.DELETE, null, Void.class);
        assertEquals(404, result.getStatusCode().value());
    }

    // @Test
    // public void updateState_ValidId_Success() throws Exception {
    //     PlayerCurrentState state = new PlayerCurrentState(CONSTANTVARIABLES.DEFAULTYEAR, CONSTANTVARIABLES.DEFAULTSTATE);
    //     URI uri = new URI(baseUrl + port + "/api/" + state.getId().longValue());
    //     PlayerCurrentState newState = new PlayerCurrentState(CONSTANTVARIABLES.DEFAULTYEAR, CONSTANTVARIABLES.DEFAULTSTATE);
    //     ResponseEntity<PlayerCurrentState> result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(newState), PlayerCurrentState.class);
    //     assertEquals(200, result.getStatusCode().value());
    //     assertEquals(state.getCurrentState(), result.getBody().getCurrentState());
    //     assertEquals(state.getYearValue(), result.getBody().getYearValue());
    // }
    // @Test
    // public void updateState_ValidId_Failure() throws Exception {
    //     URI uri = new URI(baseUrl + port + "/api/id/1");
    //     PlayerCurrentState newState = new PlayerCurrentState(CONSTANTVARIABLES.DEFAULTYEAR, CONSTANTVARIABLES.DEFAULTSTATE);
    //     ResponseEntity<PlayerCurrentState> result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(newState), PlayerCurrentState.class);
    //     assertEquals(200, result.getStatusCode().value());
    //     assertEquals(newState.getCurrentState(), result.getBody().getCurrentState());
    //     assertEquals(newState.getYearValue(), result.getBody().getYearValue());
    // }
}
