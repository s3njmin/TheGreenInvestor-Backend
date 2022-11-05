package G2T6.G2T6.G2T6;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;

import G2T6.G2T6.G2T6.models.Option;
import G2T6.G2T6.G2T6.models.Question;
import G2T6.G2T6.G2T6.repository.OptionRepository;
import G2T6.G2T6.G2T6.repository.QuestionRepository;
import G2T6.G2T6.G2T6.models.security.User;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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

	private HttpHeaders normalUserHeaders;

	private HttpHeaders adminUserHeaders;
	
	@BeforeAll()
	void createUser() throws URISyntaxException {
        // Creating an admin user for test
        User adminUser = new User("johnTheAdmin", "johnny@gmail.com",
                encoder.encode("myStrongPw"), "ROLE_ADMIN", false);
        usersRepo.save(adminUser);

		// Creating normal user for test
		User normalUser = new User("bobTheNormie", "bobby@gmail.com",
                encoder.encode("password"), "ROLE_USER", false);
        usersRepo.save(normalUser);

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
		normalUserHeaders = headers;

		// Generate Headers (Authentication as Admin User)
		URI uriLogin2 = new URI(baseUrl + port + "/api/auth/signin");
		LoginRequest loginRequest2 = new LoginRequest();
		loginRequest2.setUsername("bobTheNormie");
		loginRequest2.setPassword("password");
		HttpHeaders headers2 = new HttpHeaders();
		headers2.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<LoginRequest> entity2 = new HttpEntity<>(loginRequest2, headers2);
		ResponseEntity<JwtResponse> responseEntity2 = restTemplate.exchange(
				uriLogin2,
				HttpMethod.POST, entity2, JwtResponse.class);
		headers2.add("Authorization", "Bearer " + responseEntity2.getBody().getAccessToken());
		adminUserHeaders = headers2;
    }

	@AfterEach
	void tearDown() {
		// clear the database after each test
		refreshRepo.deleteAll();
		questions.deleteAll();
		usersRepo.deleteAll();
	}

	// @GetMapping("/questions")
	@Test
	public void listQuestions_NoQuestions_ReturnEmptyList() throws Exception {
		URI uri = new URI(baseUrl + port + "/api/questions");

		ResponseEntity<Question[]> result = restTemplate.getForEntity(uri, Question[].class);
		Question[] savedQuestions = result.getBody();

		assertEquals(200, result.getStatusCode().value());
		assertEquals(0, savedQuestions.length);
	}

	@Test
	public void listQuestions_HaveQuestions_ReturnQuestions() throws Exception {
		URI uri = new URI(baseUrl + port + "/api/questions");
		Question q1 = questions.save(new Question("Question 1", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img11.jpg", true));
		Question q2 = questions.save(new Question("Question 2", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img11.jpg", false));

		ResponseEntity<Question[]> result = restTemplate.getForEntity(uri, Question[].class);
		Question[] savedQuestions = result.getBody();

		assertEquals(200, result.getStatusCode().value());
		assertEquals(2, savedQuestions.length);

		q1.setOptions(new ArrayList<Option>()); // for verification (otherwise will be null)
		q2.setOptions(new ArrayList<Option>()); // for verification (otherwise will be null)

		assertEquals(q1, savedQuestions[0]);
		assertEquals(q2, savedQuestions[1]);
	}


	// GetMapping("/questions/{id}")
	@Test
	public void getQuestion_ValidQuestionId_Success() throws Exception {
		Question question = questions.save(new Question("Question 1", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img11.jpg", true));
		URI uri = new URI(baseUrl + port + "/api/questions/" + question.getId());
		
		ResponseEntity<Question> result = restTemplate.getForEntity(uri, Question.class);
		
		question.setOptions(new ArrayList<Option>()); // for verification (otherwise will be null)

		assertEquals(200, result.getStatusCode().value());
		assertEquals(question, result.getBody());
	}

	@Test
	public void getQuestion_InvalidQuestionId_Failure() throws Exception {
		URI uri = new URI(baseUrl + port + "/api/questions/1");

		ResponseEntity<Question> result = restTemplate.getForEntity(uri, Question.class);

		assertEquals(404, result.getStatusCode().value());
	}

	// PostMapping("/questions")
	@Test
	public void addQuestion_newQuestion_Success() throws Exception {
		URI uri = new URI(baseUrl + port + "/api/questions");
		Question question = new Question(1L, "Question 1", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img11.jpg", null, true);

		ResponseEntity<Question> result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(question, adminUserHeaders), Question.class);

		assertEquals(201, result.getStatusCode().value());
		assertEquals(question, result.getBody());
	}

	@Test
	public void addQuestion_existingQuestion_Failure() throws Exception {
		URI uri = new URI(baseUrl + port + "/api/questions");
		Question question = questions.save(new Question("Question 1", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img11.jpg", true));

		ResponseEntity<Question> result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(question, adminUserHeaders), Question.class);

		assertEquals(409, result.getStatusCode().value());
	}


	@Test
	public void addQuestion_NonAdmin_Failure() throws Exception {
		// Use Headers to Authenticate and Test
		URI uri = new URI(baseUrl + port + "/api/questions");
		Question question = new Question(1L, "Question 1", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img11.jpg", null, true);

		ResponseEntity<Question> result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(question, normalUserHeaders), Question.class);

		assertEquals(403, result.getStatusCode().value());
	}

	@Test
	public void addQuestion_Admin_Success() throws Exception {
		// Use Headers to Authenticate and Test
		URI uri = new URI(baseUrl + port + "/api/questions");
		Question question = new Question(1L, "Question 1", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img11.jpg", null, true);

		ResponseEntity<Question> result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(question, adminUserHeaders), Question.class);

		assertEquals(201, result.getStatusCode().value());
		assertEquals(question, result.getBody());
	}

	// @PutMapping("/questions/{id}")
	@Test
	public void updateQuestion_invalidQuestion_Failure() throws Exception {
		Question question = new Question("Question 1", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img11.jpg", true);
		URI uri = new URI(baseUrl + port + "/api/questions/" + 1);
		
		ResponseEntity<Question> result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(question), Question.class);

		assertEquals(404, result.getStatusCode().value());
	}

	@Test
	public void updateQuestion_validQuestion_Success() throws Exception {
		Question question = questions.save(new Question("Question 1", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img11.jpg", true));
		Question updatedQuestion = new Question(question.getId(), "Question 2", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img12.jpg", new ArrayList<Option>(), false);
		
		URI uri = new URI(baseUrl + port + "/api/questions/" + question.getId());
		ResponseEntity<Question> result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(updatedQuestion), Question.class);

		assertEquals(200, result.getStatusCode().value());
		assertEquals(updatedQuestion, result.getBody());
	}

	// @DeleteMapping("/questions/{id}")
	@Test
	public void deleteQuestion_validQuestion_Success() throws Exception {
		Question question = questions.save(new Question("Question 1", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img11.jpg", true));

		URI uri = new URI(baseUrl + port + "/api/questions/" + question.getId());
		ResponseEntity<Void> result = restTemplate.exchange(uri, HttpMethod.DELETE, null, Void.class);

		assertEquals(200, result.getStatusCode().value());
		assertNull(result.getBody());
	}

	@Test
	public void deleteQuestion_invalidQuestion_Failure() throws Exception {
		URI uri = new URI(baseUrl + port + "/api/questions/1");
		
		ResponseEntity<Void> result = restTemplate.exchange(uri, HttpMethod.DELETE, null, Void.class);

		assertEquals(404, result.getStatusCode().value());
	}
}