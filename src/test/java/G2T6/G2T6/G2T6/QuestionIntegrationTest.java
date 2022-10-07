package G2T6.G2T6.G2T6;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import G2T6.G2T6.G2T6.questions.*;
import G2T6.G2T6.G2T6.repository.UserRepository;
import G2T6.G2T6.G2T6.models.*;


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
	private UserRepository users;

	@Autowired
	private BCryptPasswordEncoder encoder;

    @AfterEach
	void tearDown(){
		// clear the database after each test
		questions.deleteAll();
		users.deleteAll();
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
		URI uri = new URI(baseUrl + port + "/questions");
        Question question = new Question("Bitcoin bad?");

        Set<Role> roles = new HashSet<Role>();
        roles.add(new Role(ERole.ROLE_ADMIN));

		users.save(new User("johnTheAdmin", "johnny@gmail.com" ,encoder.encode("goodpassword"), roles));

		ResponseEntity<Question> result = restTemplate.withBasicAuth("johnTheAdmin", "goodpassword")
										.postForEntity(uri, question, Question.class);
			
		assertEquals(201, result.getStatusCode().value());
		assertEquals(question.getQuestion(), result.getBody().getQuestion());
	}
}
