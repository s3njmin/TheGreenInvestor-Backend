package G2T6.G2T6.G2T6;

import G2T6.G2T6.G2T6.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TheGreenInvestorApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(TheGreenInvestorApplication.class, args);

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
