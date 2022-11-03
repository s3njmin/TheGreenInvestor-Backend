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
			oRepo.saveAndFlush(option);
		}
	}

	public static void saveARepo(ArticleRepository aRepo, List<Article> articleList) {
		for (Article article : articleList) {
			aRepo.saveAndFlush(article);
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
				"Company stakeholders are pressuring you to eliminate paper waste in every office. Will you:",
				"https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img1.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		qList.add(new Question("The company has earned a record profit for the year. You decide to use the money for:",
				"https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img2.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		qList.add(new Question("It is 22 April, AKA Earth Day! Your company decide to celebrate in the following way:",
				"https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img3.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		qList.add(new Question("Some employees are complaining that we can do better to reduce wastage at work. You decided to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img4.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));

		qList.add(new Question("An internal company sustainability report has found out that the distribution and logistics arm of the company can improve its sustainability practices. You decide to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img5.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		
		qList.add(new Question("Company stakeholders have a set a goal to achieve a more sustainable supply chain within the next 5 years. You decide to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img6.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		
		qList.add(new Question("Temperature has been rising considerably the past few months, and so has the electricity bill due to lower thermostat in the office. To combat this, you decide to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img7.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		
		// events
		qList.add(new Question("The illegal logging of endagered trees in Imaginationland has gone viral. The public are pressuring companies to stop using wood from endangered species such as teak, mahagony and rosewood. As a company that uses these wood for manufacturing, you decide to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img8.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		qList.add(new Question("A worldwide pandemic has started and has impacted businesses everywhere. To ensure continued green sustainability practices within the company during trying times, you decide to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img9.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		qList.add(new Question("A new change in the government has led to a more aggressive push to achieve net zero carbon emission in the country. Facing pressure from the government, you decide to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img10.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));


		// open-ended
		qList.add(new Question("Name 3 main causes of greenhouse gas emissions:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img11.jpg", CONSTANTVARIABLES.OPEN_ENDED));
		qList.add(new Question("Name 3 countries that contribute the most to carbon emissions:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/img12.jpg", CONSTANTVARIABLES.OPEN_ENDED));
		List<Option> oList = new ArrayList<Option>();

		// Question 1's Options
		oList.add(new Option("Implementing paperless office", "To implement a paperless office would require additional investment into cloud security and technology. However, it will eliminate paper waste entirely and would help implement a more streamline and efficient work environment online", qList.get(0), 180, 0, 5, 20));
		oList.add(new Option("Use recycled printing paper", "Although recycled paper costs more, it is a great way to promote sustainably within the company", qList.get(0), 120, 0, -5, 10));
		oList.add(new Option("Enforce double sided printing", "Enforcing double sided printing is a great way to prevent paper waste, however, some employees are annoyed by the new policy", qList.get(0), 120, -5, 3, 5));
		oList.add(new Option("Lie to the stakeholders", "You decided to lie to your stakeholders that paper waste has been eliminated in the office.", qList.get(0), -60, 0, 0, 0));
		oList.add(new Option("Put a limit on each employee on how much paper they can use", "Limiting employees paper use is one way of eliminating paper waste, however it is counter productive to your employees work. Morale has dropped considerably", qList.get(0), 60, -15, 2, 0));
		oList.add(new Option("Do nothing", "You decided to do nothing to combat paper waste within the company", qList.get(0), -60, 0, 0, 0));
		qList.get(0).setOptions(oList);
		qRepo.saveAndFlush(qList.get(0));
		saveORepo(oRepo, oList);
		oList.clear();

		// Question 2's Options
		oList.add(new Option("Company wide celebration party", "Everyone had a great time, and your employees morale has increased!", qList.get(1), 0, 15, 0, 5));
		oList.add(new Option("Invest money to buy solar panels on the rooftop", "Installing solar panels are a cheaper and cleaner alternative for electricity!", qList.get(1), 180, 0, 5, 10));
		oList.add(new Option("Upgrade existing machinery to a more carbon friendly version", "Despite the high costs of new machinery, upgrading our equipment would help contribute to the company's carbon neutral goal tremendously", qList.get(1), 250, 0, 0, 20));
		oList.add(new Option("Increase your salary", "Word has start to spread among your company regarding your increase in salary. Your selfish act has caused the companys morale to plumet.", qList.get(1), 0, -15, -5, 10));
		oList.add(new Option("Expand the business further by hiring new people and machinery", "Expanding our business in an unsustainable manner will affect the companys long term vision for sustainability.", qList.get(1), -180, 0, 10, 35));
		oList.add(new Option("Do nothing", "Your employees did not feel rewarded, and morale has dropped slightly.", qList.get(1), 0, -5, 0, 0));
		qList.get(1).setOptions(oList);
		qRepo.saveAndFlush(qList.get(1));
		saveORepo(oRepo, oList);
		oList.clear();
		
		// Question 3's Options
		oList.add(new Option("Encourage all employees to cycle or carpool to work", "Cycling or carpooling is a good strategy to make our commute more sustainable as compared to public transport and personal vehicles.", qList.get(2), 180, 0, 0, 5));
		oList.add(new Option("Offer only vegetarian lunch choices for a week at work", "A vegetarian diet means 2.5x less carbon emissions. However, some employees are upset as they are now forced to eat vegetarian meals.", qList.get(2), 60, -5, 0, 10));
		oList.add(new Option("Organize a company beach cleanup", "Not only are we keeping the beaches clean, but your employees are enjoying the company outing", qList.get(2), 180, 10, 0, 15));
		oList.add(new Option("Give your employees an off day", "Your employees are happy that they are given an off day!", qList.get(2), 0, 10, 0, 5));
		oList.add(new Option("Educate your employees on importance of sustainability", "Educating your employees on sustainably can help promote a green culture mentality around the company.", qList.get(2), 60, 0, 0, 5));
		oList.add(new Option("Do nothing", "You decide to do nothing...", qList.get(2), 0, 0, 0, 0));
		qList.get(2).setOptions(oList);
		qRepo.saveAndFlush(qList.get(2));
		saveORepo(oRepo, oList);
		oList.clear();

		// Question 4's Options
		oList.add(new Option("Remove plastic cups at work and enforce Bring Your Own Bottle policy", "Plastic cups are bad for the environment as they take upto 1000 years to breakdown! Enforcing BYOB policy is a sustainable practice that will help the environment", qList.get(3), 180, 5, 0, 10));
		oList.add(new Option("Encourage employees to avoid use of disposable items and use reusable containers for lunch", "Disposable itemsmade of plastic don't biodegrade, but break down into micro particles that contaminate our environment. Encouraging use of reusable containers is a sustainable practice that will help the environment", qList.get(3), 180, 5, 0, 10));
		oList.add(new Option("Send an email company wide to focus on work productivity rather than wastage at work", "You decide to do the worst thing imaginable. Your employees are outraged, and some are already resigning from the company!", qList.get(3), 0, -25, -5, 5));
		oList.add(new Option("Install a color coded recycling bin at work", "Color-coded recyling bins can encourage and promote recycling at work", qList.get(3), 120, 5, 0, 15));
		oList.add(new Option("Place posters around the office that advocate for importance of preventing wastage", "Placing sustainability and climate posters around the office is a good way to educate and promote importance of sustainability, but some employees find it annoying", qList.get(3), 60, -5, 0, 1));
		oList.add(new Option("Do nothing", "You decided to do nothing to address your employees complaints...", qList.get(3), 0, -15, 0, 0));
		qList.get(3).setOptions(oList);
		qRepo.saveAndFlush(qList.get(3));
		saveORepo(oRepo, oList);
		oList.clear();

		// Question 5's Options
		oList.add(new Option("Change to electric delivery vehicles", "Upgrading to electric delivery vehicles is costly, but they are a cleaner and cheaper alternative to petrol", qList.get(4), 320, 0, 4, 35));
		oList.add(new Option("Use more sustainable material for packing goods for delivery", "Large amounts of packaging produced today cannot be recycled in existing recycling systems. Upgrading to a sustainble packaging material promotes sustainability, even if costs more", qList.get(4), 250, 0, -5, 25));
		oList.add(new Option("Using route optimization for deliveries", "Utilizing route optimization ensures effectiveness and efficiency, which in turns reduce overall carbon emission and cost from vehicles. Employees are also happy as they can now work more efficiently", qList.get(4), 250, 5, 5, 20));
		oList.add(new Option("Start outsourcing other companies to perform our distribution and logistics needs", "Outsourcing to other companies would prevent us from analysing and encouraging sustainable practice within the supply chain. Some employees are also upset as they fear their job safety", qList.get(4), -220, -10, 3, 15));
		oList.add(new Option("Improve on existing logistics operation to be more efficient", "Streamlining existing logistics operation ensure efficiency and effectiveness, which could prevent time, energy and material waste", qList.get(4), 60, 5, 2, 10));
		oList.add(new Option("Ignore the report", "You decided to ignore the report...", qList.get(4), -120, 0, 0, 0));
		qList.get(4).setOptions(oList);
		qRepo.saveAndFlush(qList.get(4));
		saveORepo(oRepo, oList);
		oList.clear();

		// Question 6's Options
		oList.add(new Option("Change suppliers that have a more sustainable and eco friendly policies, even thought it could cost more", "Changing to a sustainable and reputable supplier helps ensure sustainability within your companies supply chain, even if you have to reduce your profitablity. Your customers and employees would be happy to learn that our products and services promote sustainbility practice", qList.get(5), 320, 5, -10, 20));
		oList.add(new Option("Encourage current suppliers to be more sustainably sourced ", "Communicating and working with existing suppliers to promote sustainbility is a huge challenge. However, it could promote strong ties and relation with your supplier if they share the same goal.", qList.get(5), 180, 0, 0, 10));
		oList.add(new Option("Redesign products to minimise production complexity, reduce energy consumption and increase product life span", "Product redesign is an excellent way to ensure sustainainble business practices in the long run. In the long run, the company could save more in manufacturing", qList.get(5), 320, 0, 5, 25));
		oList.add(new Option("Invest in automation to start cutting down on manpower", "Investing in automation helps to reduce waste and the companies carbon footprint. However, some employees would lose their jobs in the long run", qList.get(5), 250, -30, 10, 35));
		oList.add(new Option("Install more plants around the warehouse", "Unforunately, having more greenery around the warehouse contributes little to sustainability. On the bright side, your employees seem more happy!", qList.get(5), 0, 5, 0, 5));
		oList.add(new Option("Ignore the goal", "You decided to ignore the goal set by your stakeholders...", qList.get(5), 0, 0, 0, 0));
		qList.get(5).setOptions(oList);
		qRepo.saveAndFlush(qList.get(5));
		saveORepo(oRepo, oList);
		oList.clear();
		
		// Question 7's Options
		oList.add(new Option("Enforce the thermostat value so no one can change it", "Enforcing thermostat temperature helps to save energy and money. But some employees are upset", qList.get(6), 60, -5, 0, 5));
		oList.add(new Option("Allow employees freedom to set the value to be lower", "Employees are happy, but we would be wasting energy and money", qList.get(6), -60, 5, -2, 5));
		oList.add(new Option("Encourage employees to work from the convenience and comfort of their own home", "Working from home reduces carbon emissions from business operations and transport. Employees are also more happy and productive when working from home!", qList.get(6), 180, 10, 2, 10));
		oList.add(new Option("Turn off the air condition and only use standing fans", "You might be saving energy and costs, but your employees deserve a decent working environment!", qList.get(6), 120, -15, 0, 5));
		oList.add(new Option("Upgrade to a more eco-friendly air conditioner", "Upgrading to a more eco-friendly air conditioner helps reduce the companies carbon footprint", qList.get(6), 180, 5, 0, 10));
		oList.add(new Option("Implement nudist company policy", "NUDIST POLICY? Some employees might be happy, but I think you belong in jail", qList.get(6), 0, -100, 0, 0));
		qList.get(6).setOptions(oList);
		qRepo.saveAndFlush(qList.get(6));
		saveORepo(oRepo, oList);
		oList.clear();

		// Question 8's Options
		oList.add(new Option("Ignore the protests and continue with business operations", "You decided to ignore the protests, but that affected the companies public image", qList.get(7), -480, 0, -15, 0));
		oList.add(new Option("Release a statement stating the company is not going changing its business practices", "Your statement has caused public outrage, and many are now avoiding your products and services", qList.get(7), -480, -20, -20, 1));
		oList.add(new Option("Source for these wood from sustainable and reputable suppliers that replant trees", "Finding sustainable wood suppliers ensures our product are sustainably sourced", qList.get(7), 280, 0, 0, 25));
		oList.add(new Option("Start using plywood", "Plywood is a cheaper but is of poor quality. It is still sustainably sourced", qList.get(7), 180, 0, 0, 5));
		oList.add(new Option("Stop using wood entirely in your products", "Finding alternative material for your products is extremely costly and will affect future sales", qList.get(7), 60, 0, -15, 35));
		oList.add(new Option("Close down your business entirely", "You decide to close down your busines... Game over?", qList.get(7), 0, 0, 0, 0));
		qList.get(7).setOptions(oList);
		qRepo.saveAndFlush(qList.get(7));
		saveORepo(oRepo, oList);
		oList.clear();
		
		// Question 9's Options
		oList.add(new Option("Gift every employees with reusable cloth masks", "Reusable cloth masks is a more sustainable and more environmentally friendly alternative to single use face mask. Your employees appreciate the gift as well", qList.get(8), 60, 5, 0, 5));
		oList.add(new Option("Obtain infrastructure to allow employees to work remotely from home", "Working from home ensures health and safety of employees. It also reduces carbon emissions from business operations and transport. Employees are also more happy and productive when working from home!", qList.get(8), 320, 10, 5, 15));
		oList.add(new Option("Close business temporarily, and hope that pandemic will end soon", "You decided to close the business... Unfortunately, just like COVID, it lasted several years...", qList.get(8), 0, -25, -15, 30));
		oList.add(new Option("Do nothing", "You decided to do nothing... As the pandemic worsens, your business operations were affected and were not able to adapt to the new business environment. Your business made a loss and morale plummets drastically", qList.get(8), 0, -25, -20, 0));
		oList.add(new Option("Make a statement declaring the pandemic is fake", "Unfortunately, the pandemic is as real as it gets. The public and the employee has lost faith in you and the company. On the other hand, you have gained a following of pseudointellectuals who believe in ridiculous conspiracies", qList.get(8), 0, -50, -20, 0));
		oList.add(new Option("Redesign and optimise business operations during pandemic", "Analysing and tweaking business operations is ideal now that economic landscape has changed due to the pandemic", qList.get(8), 0, 10, 10, 10));
		qList.get(8).setOptions(oList);
		qRepo.saveAndFlush(qList.get(8));
		saveORepo(oRepo, oList);
		oList.clear();
		
		// Question 10's Options
		oList.add(new Option("Relocate businesses elsewhere", "Relocating your business to another country with less environmental regulations encourages the business to not practice sustainability. It will also be extremely costly to migrate the company to another location. ", qList.get(9), -320, 0, -25, 50));
		oList.add(new Option("Push a marketing campaign to highlight the companies green sustainable policy to improve public image", "Pushing a marketing campaign helps improve your business public image, however it does nothing to contribute to your company's sustainability goals", qList.get(9), -180, 5, 0, 10));
		oList.add(new Option("Hang up posters around office to educate employees on reducing wastage and promote recycling at work", "Placing sustainability and climate posters around the office is a good way to educate and promote importance of sustainability. But it doesn't do enough to meet the government's goal for net zero carbon emission", qList.get(9), 0, 5, 0, 5));
		oList.add(new Option("Conduct a carbon assessment of your business to find ways to achieve net zero carbon emissions", "Conducting carbon assessment is a great way for your business to start using more sustainable practices in the future", qList.get(9), 480, 0, 0, 20));
		oList.add(new Option("Dedicate more time and resources for more sustainability initiatives within the company", "With more investment into internal sustainability policies, we ensure the company is aligned with the government green goals", qList.get(9), 320, 0, 0, 15));
		oList.add(new Option("Do nothing", "You decide to do nothing, and the goverment decided to impose a hefty green tax on the company for your inactivity", qList.get(9), -180, 0, -20, 0));
		qList.get(9).setOptions(oList);
		qRepo.saveAndFlush(qList.get(9));
		saveORepo(oRepo, oList);
		oList.clear();

		// Question 11's Options (open-ended) treating options as answers
		oList.add(new Option("Electricity and Heat Production", "null", qList.get(10)));
		oList.add(new Option("Agriculture and Land Use Changes", "null", qList.get(10)));
		oList.add(new Option("Industry", "null", qList.get(10)));
		oList.add(new Option("Transportation", "null", qList.get(10)));
		oList.add(new Option("Buildings", "null", qList.get(10)));
		oList.add(new Option("Extraction/Processing of fossil fuels", "null", qList.get(10)));
		qList.get(10).setOptions(oList);
		qRepo.saveAndFlush(qList.get(10));
		saveORepo(oRepo, oList);
		oList.clear();
	
		// Question 12's Options (open-ended) treating options as answers
		oList.add(new Option("China", "null", qList.get(11)));
		oList.add(new Option("United States", "null", qList.get(11)));
		oList.add(new Option("India", "null", qList.get(11)));
		oList.add(new Option("Russia", "null", qList.get(11)));
		oList.add(new Option("Japan", "null", qList.get(11)));
		oList.add(new Option("Germany", "null", qList.get(11)));
		qList.get(11).setOptions(oList);
		qRepo.saveAndFlush(qList.get(11));
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
//		testRepo.saveAndFlush(ck);
//		testRepo.saveAndFlush(kami);

//		System.out.println(ck.getCurrentState());
//		System.out.println(kami.getCurrentState());

	}

}
