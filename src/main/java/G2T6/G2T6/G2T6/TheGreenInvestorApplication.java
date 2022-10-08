package G2T6.G2T6.G2T6;

import G2T6.G2T6.G2T6.StateMachine.StateRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class TheGreenInvestorApplication {
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(TheGreenInvestorApplication.class, args);
		StateRepository UserRepo = ctx.getBean(StateRepository.class);
		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);
		template.execute("DROP TABLE GameStats IF EXISTS");

		template.execute("insert into ROLES (id, name) values (1, 'ROLE_USER')");
		template.execute("insert into ROLES (id, name) values (2, 'ROLE_MODERATOR')");
		template.execute("insert into ROLES (id, name) values (3, 'ROLE_ADMIN')");
		template.execute("insert into ROLES (id, name) values (4, 'ROLE_SUPER_ADMIN')");
		template.execute("insert into ROLES (id, name) values (5, 'ROLE_SUPER_USER')");
		template.execute("insert into ROLES (id, name) values (6, 'ROLE_SUPER_MODERATOR')");

		// BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// String pw = encoder.encode("password");
		// template.execute("insert into USERS (id, username, email, password) values (1, 'johnTheAdminTest', 'johnnyAdminTest@gmail.com', '" + pw + "')");


		// template.execute("insert into USER_ROLES (user_id, role_id) values (1, 3)");

//		System.out.println("[Add GameStats]: " + repo.save(new GameStats(1l, 2, 3, 4)));
//		System.out.println("[Add GameStats]: " + repo.save(new GameStats(2l, 0, 0, 0)));

		//System.out.println("[Add usersession]: " + UserRepo.save(new PlayerCurrentState(1L, CONSTANTVARIABLES.DEFAULTYEAR, CONSTANTVARIABLES.DEFAULTSTATE)));

		//template.execute("CREATE TABLE USERS(id int, email varchar(255), password varchar(255), username varchar(255))");

		//template.execute("CREATE TABLE USER_ROLES(id int, user_id int, role_id int)");

		//template.execute("CREATE TABLE HIBERNATE_SEQUENCE (sequence_name varchar(255), next_val int)");

		//template.execute("CREATE TABLE REFRESH_TOKEN (id int, expiry_date timestamp, token varchar(255), user_id int)");


		//RestTemplateClient client = ctx.getBean(RestTemplateClient.class);

//
//		// JPA book repository init
//		BookRepository books = ctx.getBean(BookRepository.class);
//		System.out.println("[Add book]: " + books.save(new Book("Spring Security Fundamentals")).getTitle());
//		System.out.println("[Add book]: " + books.save(new Book("Gone With The Wind")).getTitle());
//
//		// JPA user repository init
//		UserRepository users = ctx.getBean(UserRepository.class);
//		BCryptPasswordEncoder encoder = ctx.getBean(BCryptPasswordEncoder.class);
//		System.out.println("[Add user]: " + users.save(
//				new User("admin", encoder.encode("goodpassword"), "ROLE_ADMIN")).getUsername());
//
//
//
//		// Test the RestTemplate client with authentication
//		/**
//		 * TODO: Activity 3 (after class)
//		 * Uncomment the following code and test the changes
//		 * Here we use our own Rest client to test the service
//		 * Authentication info has been added int the RestTemplateClient.java
//		 */
//
//		RestTemplateClient client = ctx.getBean(RestTemplateClient.class);
//		System.out.println("[Add book]: " + client.addBook("http://localhost:8080/books", new Book("Spring in Actions")).getTitle());
//
//		// Get the 1st book, obtain a HTTP response and print out the title of the book
//		System.out.println("[Get book]: " + client.getBookEntity("http://localhost:8080/books", 1L).getBody().getTitle());

	}

}
