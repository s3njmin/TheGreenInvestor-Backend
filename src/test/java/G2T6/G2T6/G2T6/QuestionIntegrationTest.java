package G2T6.G2T6.G2T6;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import G2T6.G2T6.G2T6.models.Question;
import G2T6.G2T6.G2T6.models.security.User;
import G2T6.G2T6.G2T6.repository.QuestionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.util.*;

import G2T6.G2T6.G2T6.repository.RefreshTokenRepository;
import G2T6.G2T6.G2T6.repository.UserRepository;
import G2T6.G2T6.G2T6.payload.request.*;
import G2T6.G2T6.G2T6.payload.response.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuestionIntegrationTest {

	@LocalServerPort
	private int port;

	private final String baseUrl = "http://localhost:";

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private QuestionRepository questions;

	@Autowired
	private UserRepository usersRepo;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private RefreshTokenRepository refreshRepo;
	
	@BeforeEach()
	void createUser() {

        // Making an admin user for test
        User newUser = new User("johnTheAdmin", "johnny@gmail.com",
                encoder.encode("myStrongPw"), "ROLE_ADMIN");
        usersRepo.save(newUser);
    }

	@AfterEach
	void tearDown() {
		// clear the database after each test
		refreshRepo.deleteAll();
		questions.deleteAll();
		usersRepo.deleteAll();
	}

	@Test
	public void getQuestions_Success() throws Exception {
		URI uri = new URI(baseUrl + port + "/api/questions");
		questions.save(new Question("Ice caps melting, how?"));

		ResponseEntity<Question[]> result = restTemplate.getForEntity(uri, Question[].class);
		Question[] questions = result.getBody();

		assertEquals(200, result.getStatusCode().value());
		assertEquals(1, questions.length);
	}

	@Test
	public void getQuestion_ValidQuestionId_Success() throws Exception {
		Question question = new Question("Eat beef?");
		Long id = questions.save(question).getId();
		URI uri = new URI(baseUrl + port + "/api/questions/" + id);

		ResponseEntity<Question> result = restTemplate.getForEntity(uri, Question.class);

		assertEquals(200, result.getStatusCode().value());
		assertEquals(question.getQuestion(), result.getBody().getQuestion());
	}

	@Test
	public void getQuestion_InvalidQuestionId_Failure() throws Exception {
		URI uri = new URI(baseUrl + port + "/api/questions/1");

		ResponseEntity<Question> result = restTemplate.getForEntity(uri, Question.class);

		assertEquals(404, result.getStatusCode().value());
	}

	@Test
	public void addQuestion_Success() throws Exception {

		//Login as admin
		URI loginUri = new URI(baseUrl + port + "/api/auth/signin");
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("johnTheAdmin");
		loginRequest.setPassword("myStrongPw");

		HttpHeaders loginHeaders = new HttpHeaders();
		loginHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, loginHeaders);

		ResponseEntity<JwtResponse> responseEntity = restTemplate.exchange(
				loginUri,
				HttpMethod.POST, entity, JwtResponse.class);

		String jwtToken = responseEntity.getBody().getAccessToken();

		// Using JWT token as header

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

		URI uri = new URI(baseUrl + port + "/api/questions");
		Question question = new Question("Bitcoin bad?");

		// ResponseEntity<Question> result = restTemplate.withBasicAuth("johnTheAdmin",
		// "goodpassword")
		// .postForEntity(uri, question, Question.class);

		ResponseEntity<Question> result = restTemplate.exchange(uri, HttpMethod.POST,
				new HttpEntity<>(question, headers), Question.class);

		assertEquals(201, result.getStatusCode().value());
		assertEquals(question.getQuestion(), result.getBody().getQuestion());

	}

}