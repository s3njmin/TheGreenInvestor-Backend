package G2T6.G2T6.G2T6;

import G2T6.G2T6.G2T6.models.*;
import G2T6.G2T6.G2T6.repository.OptionRepository;
import G2T6.G2T6.G2T6.repository.QuestionRepository;
import G2T6.G2T6.G2T6.repository.StateRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class TheGreenInvestorApplication {

	public static void saveQRepo(QuestionRepository qRepo, List<Question> questionList) {
		for (Question q : questionList) {
			qRepo.save(q);
		}
	}

	public static void saveORepo(OptionRepository oRepo, List<Option> optionList) {
		for (Option option : optionList) {
			oRepo.save(option);
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(TheGreenInvestorApplication.class, args);
		StateRepository UserRepo = ctx.getBean(StateRepository.class);
		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);

		QuestionRepository qRepo = ctx.getBean(QuestionRepository.class);
		OptionRepository oRepo = ctx.getBean(OptionRepository.class);

		List<Question> qList = new ArrayList<Question>();

		//Questions
		qList.add(new Question("Company stakeholders are pressuring you to eliminate paper waste in every office. Will you:", "../assets/img1.jpg"));
		qList.add(new Question("The company has earned a record profit for the year. You decide to use the money for:", "../assets/img2.jpg"));
		qList.add(new Question("	It is 22 April, AKA Earth Day! Your company decide to celebrate in the following way:", "../assets/img3.jpg"));

		List<Option> oList = new ArrayList<Option>();

		// Question 1's Options
		oList.add(new Option("Implementing paperless office", "null", qList.get(0)));
		oList.add(new Option("Use recycled printing paper", "null", qList.get(0)));
		oList.add(new Option("Enforce double sided printing", "null", qList.get(0)));
		oList.add(new Option("Do nothing", "null", qList.get(0)));
		qList.get(0).setOptions(oList);
		qRepo.save(qList.get(0));
		saveORepo(oRepo, oList);
		oList.clear();
		//

		// Question 2's Options
		oList.add(new Option("Company wide celebration party", "null", qList.get(1)));
		oList.add(new Option("Invest money to buy solar panels on the rooftop", "null", qList.get(1)));
		oList.add(new Option("Invest in new manufacturing machinery to increase", "null", qList.get(1)));
		oList.add(new Option("Do nothing", "null", qList.get(1)));
		qList.get(1).setOptions(oList);
		qRepo.save(qList.get(1));
		saveORepo(oRepo, oList);
		oList.clear();
		//
		
		// Question 3's Options
		oList.add(new Option("Encourage all employees to cycle or carpool to work", "null", qList.get(2)));
		oList.add(new Option("Offer only vegetarian lunch choices for a week at work", "null", qList.get(2)));
		oList.add(new Option("Organize a company beach cleanup", "null", qList.get(2)));
		oList.add(new Option("Do nothing", "null", qList.get(2)));
		qList.get(2).setOptions(oList);
		qRepo.save(qList.get(2));
		saveORepo(oRepo, oList);
		oList.clear();

		// saveQRepo(qRepo, qList);

		//

		// Question q1 = new Question("What is your age?");
		// List<Option> o1 = new ArrayList<Option>();
		// o1.add(new Option("o1", "18-25", q1));
		// o1.add(new Option("o2", "18-25", q1));
		// o1.add(new Option("o3", "18-25", q1));
		// o1.add(new Option("o4", "18-25", q1));
		// q1.setOptions(o1);
		// qRepo.save(q1);
		// oRepo.save(o1.get(0));
		// oRepo.save(o1.get(1));
		// oRepo.save(o1.get(2));
		// oRepo.save(o1.get(3));

		// template.execute("DROP TABLE GameStats IF EXISTS");

		// BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// String pw = encoder.encode("password");
		// template.execute("insert into USERS (id, username, email, password) values (1, 'johnTheAdminTest', 'johnnyAdminTest@gmail.com', '" + pw + "')");

		// template.execute("insert into USER_ROLES (user_id, role_id) values (1, 3)");

//		System.out.println("[Add GameStats]: " + repo.save(new GameStats(1l, 2, 3, 4)));
//		System.out.println("[Add GameStats]: " + repo.save(new GameStats(2l, 0, 0, 0)));

		//System.out.println("[Add usersession]: " + UserRepo.save(new CurrentState(1L, CONSTANTVARIABLES.DEFAULTYEAR, CONSTANTVARIABLES.DEFAULTSTATE)));

		//template.execute("CREATE TABLE USERS(id int, email varchar(255), password varchar(255), username varchar(255))");

		//template.execute("CREATE TABLE USER_ROLES(id int, user_id int, role_id int)");

		//template.execute("CREATE TABLE HIBERNATE_SEQUENCE (sequence_name varchar(255), next_val int)");

		//template.execute("CREATE TABLE REFRESH_TOKEN (id int, expiry_date timestamp, token varchar(255), user_id int)");

	}

}
