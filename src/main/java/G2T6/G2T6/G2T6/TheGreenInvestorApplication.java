package G2T6.G2T6.G2T6;

import G2T6.G2T6.G2T6.misc.*;
import G2T6.G2T6.G2T6.models.*;
import G2T6.G2T6.G2T6.models.security.User;
import G2T6.G2T6.G2T6.repository.*;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.apache.bcel.classfile.ConstantValue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TheGreenInvestorApplication {

	public static void saveORepo(OptionRepository oRepo, List<Option> optionList) {
		for (Option option : optionList) {
			oRepo.save(option);
		}
	}

	public static void saveARepo(ArticleRepository aRepo, List<Article> articleList) {
		for (Article article : articleList) {
			aRepo.save(article);
		}
	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(TheGreenInvestorApplication.class, args);
		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);
		// public User(String username, String email, String password, String role) {
		QuestionRepository qRepo = ctx.getBean(QuestionRepository.class);
		OptionRepository oRepo = ctx.getBean(OptionRepository.class);

		ArticleRepository aRepo = ctx.getBean(ArticleRepository.class);

		List<Question> qList = new ArrayList<Question>();

		// Questions
		qList.add(new Question(
				"q1-key",
				"../assets/img1.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		qList.add(new Question("The company has earned a record profit for the year. You decide to use the money for:",
				"../assets/img2.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		qList.add(new Question("It is 22 April, AKA Earth Day! Your company decide to celebrate in the following way:",
				"../assets/img3.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));

		// open-ended
		qList.add(new Question("Name 3 main causes of greenhouse gas emissions:", "../assets/img3.jpg", CONSTANTVARIABLES.OPEN_ENDED));
		qList.add(new Question("Name 3 countries that contribute the most to carbon emissions:", "../assets/img3.jpg", CONSTANTVARIABLES.OPEN_ENDED));

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

		// Question 4's Options (open-ended) treating options as answers
		oList.add(new Option("Electricity and Heat Production", "null", qList.get(3)));
		oList.add(new Option("Agriculture and Land Use Changes", "null", qList.get(3)));
		oList.add(new Option("Industry", "null", qList.get(3)));
		oList.add(new Option("Transportation", "null", qList.get(3)));
		oList.add(new Option("Buildings", "null", qList.get(3)));
		oList.add(new Option("Extraction/Processing of fossil fuels", "null", qList.get(3)));
		qList.get(3).setOptions(oList);
		qRepo.save(qList.get(3));
		saveORepo(oRepo, oList);
		oList.clear();

		// Question 5's Options (open-ended) treating options as answers
		oList.add(new Option("China", "null", qList.get(4)));
		oList.add(new Option("United States", "null", qList.get(4)));
		oList.add(new Option("India", "null", qList.get(4)));
		oList.add(new Option("Russia", "null", qList.get(4)));
		oList.add(new Option("Japan", "null", qList.get(4)));
		oList.add(new Option("Germany", "null", qList.get(4)));
		qList.get(4).setOptions(oList);
		qRepo.save(qList.get(4));
		saveORepo(oRepo, oList);
		oList.clear();

		List<Article> aList = new ArrayList<Article>();
		aList.add(new Article(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
				"https://www.channelnewsasia.com/singapore/pofma-correction-direction-xbb-variant-goh-meng-seng-hardwarezone-thailand-medical-news-3007576"));
		aList.add(new Article(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. At tellus at urna condimentum mattis pellentesque. Id semper risus in hendrerit gravida. Viverra orci sagittis eu volutpat. Vehicula ipsum a arcu cursus vitae congue mauris rhoncus. In pellentesque massa placerat duis ultricies lacus sed. Pellentesque massa placerat duis ultricies lacus sed turpis tincidunt. Ac felis donec et odio pellentesque. Feugiat sed lectus vestibulum mattis ullamcorper velit sed. Consequat semper viverra nam libero justo. Tincidunt augue interdum velit euismod in pellentesque massa. Cras fermentum odio eu feugiat pretium nibh ipsum consequat nisl. Quis viverra nibh cras pulvinar mattis. Elementum pulvinar etiam non quam lacus suspendisse. Luctus venenatis lectus magna fringilla. Elementum nibh tellus molestie nunc non blandit massa. Porttitor rhoncus dolor purus non enim praesent elementum. Mauris sit amet massa vitae tortor.",
				"https://www.espn.com.sg/football/spanish-laliga/story/4771670/ruthless-real-madrid-show-how-far-barcelona-have-to-go-yet"));
		aList.add(new Article(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Pellentesque elit eget gravida cum sociis natoque penatibus. Nulla facilisi cras fermentum odio. A pellentesque sit amet porttitor eget dolor morbi. Proin fermentum leo vel orci porta non pulvinar neque. Nulla facilisi morbi tempus iaculis urna id volutpat lacus laoreet. Tellus at urna condimentum mattis pellentesque. Eget lorem dolor sed viverra ipsum nunc aliquet bibendum enim. Nisl purus in mollis nunc sed. Sit amet tellus cras adipiscing enim eu turpis egestas. Mattis pellentesque id nibh tortor. Purus non enim praesent elementum facilisis. Quam pellentesque nec nam aliquam sem et. Accumsan in nisl nisi scelerisque eu. Integer quis auctor elit sed vulputate mi sit amet. Posuere ac ut consequat semper. Nisl condimentum id venenatis a condimentum vitae sapien pellentesque. Enim nec dui nunc mattis enim ut tellus.",
				"https://www.espn.com.sg/football/manchester-united-engman_utd/story/4772297/ten-hag-defends-subbing-ronaldo-in-man-united-draw-we-have-to-rotate"));
		saveARepo(aRepo, aList);

//		User ck = new User( "ckasdasd", "ck@gmail.com", "Password1232", "GUEST");
//		User kami = new User( "kamisama", "kami@gmail.com", "Password1233", "ROLE_USER");
//		List<CurrentState> ckStates = new ArrayList<>();
//		CurrentState currentState01 = new CurrentState(1l, ck, 0, State.start, 0, "");
//		ckStates.add(currentState01);
//
//		List<GameStats> ckStats = new ArrayList<>();
//		GameStats stats01 = new GameStats(1l, 0, 0, 0, ck, currentState01);
//		ckStats.add(stats01);
//
//		currentState01.setGameStats(stats01);
//
//		List<CurrentState> kamiStates = new ArrayList<>();
//		CurrentState currentState02 = new CurrentState(2l, kami, 0, State.start, 0, "");
//		kamiStates.add(currentState02);
//
//		List<GameStats> kamiStats = new ArrayList<>();
//		GameStats stats02 = new GameStats(2l, 0, 0, 0, kami, currentState02);
//		kamiStats.add(stats02);
//
//		currentState02.setGameStats(stats02);
//
//		ck.setCurrentState(ckStates);
//		ck.setGameStats(ckStats);
//		kami.setCurrentState(kamiStates);
//		kami.setGameStats(kamiStats);
//
//		UserRepository testRepo = ctx.getBean(UserRepository.class);
//		testRepo.save(ck);
//		testRepo.save(kami);

//		System.out.println(ck.getCurrentState());
//		System.out.println(kami.getCurrentState());

	}

}
