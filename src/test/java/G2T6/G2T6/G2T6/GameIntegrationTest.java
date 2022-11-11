package G2T6.G2T6.G2T6;

import G2T6.G2T6.G2T6.misc.State;
import G2T6.G2T6.G2T6.models.CurrentState;
import G2T6.G2T6.G2T6.models.security.User;
import G2T6.G2T6.G2T6.payload.request.LoginRequest;
import G2T6.G2T6.G2T6.payload.response.GameResponse;
import G2T6.G2T6.G2T6.payload.response.JwtResponse;
import G2T6.G2T6.G2T6.payload.response.MessageResponse;
import G2T6.G2T6.G2T6.payload.response.ProfileResponse;
import G2T6.G2T6.G2T6.repository.GameStatsRepository;
import G2T6.G2T6.G2T6.repository.RefreshTokenRepository;
import G2T6.G2T6.G2T6.repository.StateRepository;
import G2T6.G2T6.G2T6.repository.UserRepository;
import G2T6.G2T6.G2T6.services.StateServiceImplementation;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.URI;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GameIntegrationTest {
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
    UserRepository usersRepo;

    @Autowired
    RefreshTokenRepository refreshRepo;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private GameStatsRepository gameRepo;

    @Autowired
    private StateServiceImplementation stateService;

    private User normalUser;

    private User adminUser;

    private String regularUserToken;

    private String adminUserToken;

    @BeforeEach()
    void createUser() throws Exception {
        // Creating an admin user for test
        User adminUser = new User("johnTheAdmin", "johnny@gmail.com",
                encoder.encode("myStrongPw"), "ROLE_ADMIN", false);

        List<CurrentState> currentStates = new ArrayList<CurrentState>();
        CurrentState defaultState = stateService.getDefaultState();
        currentStates.add(defaultState);

        adminUser.setCurrentState(currentStates);

        defaultState.setUser(adminUser);

        usersRepo.save(adminUser);

        // Creating normal user for test
        User normalUser = new User("bobTheNormie", "bobby@gmail.com",
                encoder.encode("password"), "ROLE_USER", false);

        List<CurrentState> currentStates2 = new ArrayList<CurrentState>();
        CurrentState defaultState2 = stateService.getDefaultState();
        currentStates2.add(defaultState2);

        normalUser.setCurrentState(currentStates2);

        defaultState2.setUser(normalUser);

        usersRepo.save(normalUser);
    }

    @AfterEach
    void tearDown() {
        // clear the database after each test
        refreshRepo.deleteAll();
        usersRepo.deleteAll();
        gameStatsRepo.deleteAll();
        stateRepo.deleteAll();
    }

    // called to authenticate as Admin User
    public HttpHeaders generateAuthAdmin() throws Exception {
        // Generate Headers (Authentication as Admin User)
        URI uriLogin2 = new URI(baseUrl + port + "/api/auth/signin");
        LoginRequest loginRequest2 = new LoginRequest();
        loginRequest2.setUsername("johnTheAdmin");
        loginRequest2.setPassword("myStrongPw");
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<LoginRequest> entity2 = new HttpEntity<>(loginRequest2, headers2);
        ResponseEntity<JwtResponse> responseEntity2 = restTemplate.exchange(
                uriLogin2,
                HttpMethod.POST, entity2, JwtResponse.class);
        headers2.add("Authorization", "Bearer " + responseEntity2.getBody().getAccessToken());
        return headers2;
    }

    // called to authenticate as Normal User
    public HttpHeaders generateAuthNormal() throws Exception {
        // Generate Headers (Authentication as Normal User)
        URI uriLogin = new URI(baseUrl + port + "/api/auth/signin");
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("bobTheNormie");
        loginRequest.setPassword("password");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, headers);
        ResponseEntity<JwtResponse> responseEntity = restTemplate.exchange(
                uriLogin,
                HttpMethod.POST, entity, JwtResponse.class);
        headers.add("Authorization", "Bearer " + responseEntity.getBody().getAccessToken());
        return headers;
    }

    // called to generate jwt token for an invalid user
    public HttpHeaders generateAuthInvalid() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + "JK#@K#!IamInvalidTokeneji12j3i!@#");
        return headers;
    }

    @Test
    public void getGameInfo_WithNormalUser_ShouldReturn200() throws Exception {
        // Generate Headers (Authentication as Normal User)
        HttpHeaders headers = generateAuthNormal();

        // Get Game Info
        URI uri = new URI(baseUrl + port + "/api/gameInfo");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GameResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET, entity, GameResponse.class);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void getGameInfo_WithAdminUser_ShouldReturn200() throws Exception {
        // Generate Headers (Authentication as Admin User)
        HttpHeaders headers = generateAuthAdmin();

        // Get Game Info
        URI uri = new URI(baseUrl + port + "/api/gameInfo");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GameResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET, entity, GameResponse.class);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void getGameInfo_WithInvalidUser_ShouldReturn401() throws Exception {
        // Generate Headers (Authentication as Invalid User)
        HttpHeaders headers = generateAuthInvalid();

        // Get Game Info
        URI uri = new URI(baseUrl + port + "/api/gameInfo");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GameResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET, entity, GameResponse.class);

        // Assert
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    @Test
    public void getGameInfo_WithNoUser_ShouldReturn401() throws Exception {
        // Get Game Info
        URI uri = new URI(baseUrl + port + "/api/gameInfo");
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<GameResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET, entity, GameResponse.class);

        // Assert
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    @Test
    public void getGameInfo_WithNormalUserAndStateStart_ShouldReturnStateStart() throws Exception {
        // Generate Headers (Authentication as Normal User)
        HttpHeaders headers = generateAuthNormal();

        // Get Game Info
        URI uri = new URI(baseUrl + port + "/api/gameInfo");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GameResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET, entity, GameResponse.class);

        // Assert
        assertEquals(State.start, responseEntity.getBody().getState());
        assertEquals(200, responseEntity.getStatusCodeValue());

    }

    @Test
    public void getGameInfo_withAdminUserAndStateStart_ShouldReturnStateStart() throws Exception {
        // Generate Headers (Authentication as Admin User)
        HttpHeaders headers = generateAuthAdmin();

        // Get Game Info
        URI uri = new URI(baseUrl + port + "/api/gameInfo");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GameResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET, entity, GameResponse.class);

        // Assert
        assertEquals(State.start, responseEntity.getBody().getState());
        assertEquals(200, responseEntity.getStatusCodeValue());

    }

    @Test
    public void getGameInfo_withInvalidUserAndStateStart_ShouldReturn401() throws Exception {
        // Generate Headers (Authentication as Invalid User)
        HttpHeaders headers = generateAuthInvalid();

        // Get Game Info
        URI uri = new URI(baseUrl + port + "/api/gameInfo");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GameResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET, entity, GameResponse.class);

        // Assert
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    @Test
    public void getGameInfo_withNoUserAndStateStart_ShouldReturn401() throws Exception {
        // Get Game Info
        URI uri = new URI(baseUrl + port + "/api/gameInfo");
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<GameResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET, entity, GameResponse.class);

        // Assert
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    // @Test
    // public void getGameInfo_withNormalUserAndStateCompleted_ShouldReturnStateCompleted() throws Exception {
    //     // Generate Headers (Authentication as Normal User)
    //     HttpHeaders headers = generateAuthNormal();

    //     User normalUser = userRepo.findByUsername("bobTheNormie").get();
    //     //change state of normal user to completed
    //     normalUser.getCurrentState().get(0).setCurrentState(State.completed);
    //     userRepo.save(normalUser);

    //     // Get Game Info
    //     URI uri = new URI(baseUrl + port + "/api/gameInfo");
    //     HttpEntity<String> entity = new HttpEntity<>(null, headers);
    //     ResponseEntity<GameResponse> responseEntity = restTemplate.exchange(
    //             uri,
    //             HttpMethod.GET, entity, GameResponse.class);

    //     // Assert
    //     assertEquals(State.completed, responseEntity.getBody().getState());
    //     assertEquals(200, responseEntity.getStatusCodeValue());

    // }

    @Test
    public void postStartGame_WithNormalUser_ShouldReturn200() throws Exception {
        // Generate Headers (Authentication as Normal User)
        HttpHeaders headers = generateAuthNormal();

        // Start Game
        URI uri = new URI(baseUrl + port + "/api/startGame");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GameResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST, entity, GameResponse.class);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void postStartGame_WithAdminUser_ShouldReturn200() throws Exception {
        // Generate Headers (Authentication as Admin User)
        HttpHeaders headers = generateAuthAdmin();

        // Start Game
        URI uri = new URI(baseUrl + port + "/api/startGame");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GameResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST, entity, GameResponse.class);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void postStartGame_WithInvalidUser_ShouldReturn401() throws Exception {
        // Generate Headers (Authentication as Invalid User)
        HttpHeaders headers = generateAuthInvalid();

        // Start Game
        URI uri = new URI(baseUrl + port + "/api/startGame");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GameResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST, entity, GameResponse.class);

        // Assert
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    @Test
    public void postStartGame_WithNoUser_ShouldReturn401() throws Exception {
        // Start Game
        URI uri = new URI(baseUrl + port + "/api/startGame");
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        ResponseEntity<GameResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST, entity, GameResponse.class);

        // Assert
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    @Test
    public void postStartGame_withNormalUserAndStateStart_ShouldReturnMessageGameStarted() throws Exception {
        // Generate Headers (Authentication as Normal User)
        HttpHeaders headers = generateAuthNormal();

        // Start Game
        URI uri = new URI(baseUrl + port + "/api/startGame");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<MessageResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST, entity, MessageResponse.class);

        // Assert
        assertEquals("Game started", responseEntity.getBody().getMessage());
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void submitAnswer_withNormalUserAndStateStart_ShouldReturn400() throws Exception {
        // Generate Headers (Authentication as Normal User)
        HttpHeaders headers = generateAuthNormal();

        // Submit Answer
        URI uri = new URI(baseUrl + port + "/api/submitAnswer");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<MessageResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST, entity, MessageResponse.class);

        // Assert
        assertEquals(400, responseEntity.getStatusCodeValue());
    }


}
