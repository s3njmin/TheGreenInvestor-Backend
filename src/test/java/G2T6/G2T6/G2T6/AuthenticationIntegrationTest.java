package G2T6.G2T6.G2T6;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.Optional;

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
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

import G2T6.G2T6.G2T6.questions.*;
import G2T6.G2T6.G2T6.repository.RefreshTokenRepository;
import G2T6.G2T6.G2T6.repository.*;
import G2T6.G2T6.G2T6.models.*;
import G2T6.G2T6.G2T6.payload.request.*;
import G2T6.G2T6.G2T6.payload.response.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserRepository usersRepo;

    @Autowired
    RefreshTokenRepository refreshRepo;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    RoleRepository rolesRepo;

    @BeforeEach
    void createUser() {

        // Making an admin user for test
        User newUser = new User("johnTheAdmin", "johnny@gmail.com",
        encoder.encode("myStrongPw"));
        Set<Role> roles = new HashSet<>();

        Role adminRole = rolesRepo.findByName(ERole.ROLE_ADMIN)
        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        
        roles.add(adminRole);
        newUser.setRoles(roles);
        usersRepo.save(newUser);
    }

    @AfterEach
    void tearDown() {
        // clear the database after each test
        refreshRepo.deleteAll();
        usersRepo.deleteAll();
    }

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/test-data.sql")
    public void login_Success() throws Exception {

        URI uri = new URI(baseUrl + port + "/api/auth/signin");
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("johnTheAdmin");
        loginRequest.setPassword("myStrongPw");
        // loginRequest.setUsername("johnTheAdminTest");
        // loginRequest.setPassword("password");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<JwtResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST, entity, JwtResponse.class);

        // ResponseEntity<JwtResponse> result = restTemplate.postForEntity(uri,
        // loginRequest, JwtResponse.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("johnTheAdmin", responseEntity.getBody().getUsername());
        // assertEquals(200, result.getStatusCodeValue());
        // assertEquals("johnTheAdmin", response.getUsername());
        assertNotNull(responseEntity.getBody().getAccessToken());
        assertNotNull(responseEntity.getBody().getRefreshToken());
    }
}
