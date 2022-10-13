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
		qList.add(new Question("It is 22 April, AKA Earth Day! Your company decide to celebrate in the following way:", "../assets/img3.jpg"));

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

	}

}
