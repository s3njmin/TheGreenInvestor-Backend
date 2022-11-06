package G2T6.G2T6.G2T6;

import G2T6.G2T6.G2T6.misc.*;
import G2T6.G2T6.G2T6.models.*;
import G2T6.G2T6.G2T6.models.orders.OptionOrder;
import G2T6.G2T6.G2T6.models.orders.QuestionOrder;
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

		// Dont think need this for now
		// populate questionOrders
		// QuestionOrderRepository questionOrderRepository = ctx.getBean(QuestionOrderRepository.class);
		// OptionOrderRepository optionOrderRepository = ctx.getBean(OptionOrderRepository.class);

		// for (int i = 0; i < 10; i++) {
		// 	questionOrderRepository.save(new QuestionOrder());
		// 	optionOrderRepository.save(new OptionOrder());
		// }

		// Questions
		qList.add(new Question(
				"Company stakeholders are pressuring you to eliminate paper waste in every office. Will you:",
				"https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/questions/img1.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		qList.add(new Question("The company has earned a record profit for the year. You decide to use the money for:",
				"https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/questions/img2.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		qList.add(new Question("It is 22 April, AKA Earth Day! Your company decide to celebrate in the following way:",
				"https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/questions/img3.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		qList.add(new Question("Some employees are complaining that we can do better to reduce wastage at work. You decided to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/questions/img4.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));

		qList.add(new Question("An internal company sustainability report has found out that the distribution and logistics arm of the company can improve its sustainability practices. You decide to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/questions/img5.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		
		qList.add(new Question("Company stakeholders have a set a goal to achieve a more sustainable supply chain within the next 5 years. You decide to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/questions/img6.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		
		qList.add(new Question("Temperature has been rising considerably the past few months, and so has the electricity bill due to lower thermostat in the office. To combat this, you decide to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/questions/img7.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		
		// events
		qList.add(new Question("The illegal logging of endagered trees in Imaginationland has gone viral. The public are pressuring companies to stop using wood from endangered species such as teak, mahagony and rosewood. As a company that uses these wood for manufacturing, you decide to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/questions/img8.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		qList.add(new Question("A worldwide pandemic has started and has impacted businesses everywhere. To ensure continued green sustainability practices within the company during trying times, you decide to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/questions/img9.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));
		qList.add(new Question("A new change in the government has led to a more aggressive push to achieve net zero carbon emission in the country. Facing pressure from the government, you decide to:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/questions/img10.jpg", CONSTANTVARIABLES.NOT_OPEN_ENDED));


		// open-ended
		qList.add(new Question("Name 3 main causes of greenhouse gas emissions:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/questions/img11.jpg", CONSTANTVARIABLES.OPEN_ENDED));
		qList.add(new Question("Name 3 countries that contribute the most to carbon emissions:", "https://tgi-bucket.s3.ap-southeast-1.amazonaws.com/questions/img12.jpg", CONSTANTVARIABLES.OPEN_ENDED));
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
		oList.add(new Option("Electricity and Heat Production", "Since the start of the Industrial Revolution and the advent of coal-powered steam engines, human activities have vastly increased the volume of greenhouse gases emitted into the atmosphere. It is estimated that between 1750 and 2011, atmospheric concentrations of carbon dioxide increased by 40 percent, methane by 150 percent, and nitrous oxide by 20 percent.", qList.get(10)));
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
		oList.add(new Option("China", "These countries won’t be able to reach carbon neutrality only by reducing their domestic emissions.  They will need to offset much of their carbon footprint on international carbon markets.", qList.get(11)));
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
				"Illegal logging is still going on in many parts of the world, endangering the ecosystem and biodiverity of the forests. One such case is in Indonesia, where a company has been accused of logging in peat forests and orangutan habitats by activists, whom have photographic evidence of their acts.",
				"https://www.bbc.com/news/world-asia-pacific-10798849"));
		aList.add(new Article(
				"The pandemic is no stranger to most of us, with the COVID-19 outbreak reaching to every corner of the world. Companies have had to change their business strategy and model to continue operating under the new economic landscape. Some of these new models have continued on even in a post-pandemic world such as 'remote work' and a 'digital-first' strategy.",
				"https://www.bbc.com/worklife/article/20210915-how-companies-around-the-world-are-shifting-the-way-they-work"));
		aList.add(new Article(
				"Singapore is one example of a country who has set an aim for zero carbon emissions by the year 2050. From 2024 to 2030, they will increase their carbon tax from S$25 to S$80. Companies will have to adapt and source for higher quality carbon credits to reduce their carbon footprint.",
				"https://www.reuters.com/markets/commodities/singapore-hike-carbon-tax-by-five-fold-2024-2022-02-18/"));
		saveARepo(aRepo, aList);

//		UserRepository userRepo = ctx.getBean(UserRepository.class);
//		CurrentState stateRepo = ctx.getBean(CurrentState.class);
//		GameStats gameStatsRepo = ctx.getBean(GameStats.class);
//
//		User regularUser = userRepo.save(new User("ckasdasd123", "ck123@gmail.com", "Password1232", "GUEST"));
//
//		CurrentState cs0101 = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR, State.completed, regularUser);
//		CurrentState cs0102 = new CurrentState(10, State.completed, regularUser);
//
//		List<CurrentState> csList01 = new ArrayList<>();
//		csList01.add(cs0101);
//		csList01.add(cs0102);
//
//		GameStats gs1 =  new GameStats(10,10,10, regularUser, cs0101);
//		GameStats gs2 =  new GameStats(1,1,1, regularUser, cs0102);
//		List<GameStats> gsList01 = new ArrayList<>();
//		gsList01.add(gs1);
//		gsList01.add(gs2);
//
//		regularUser.setCurrentState(csList01);
//		regularUser.setGameStats(gsList01);
//		stateRepo.save(cs0101);
//		stateRepo.save(cs0102);
//		gameStatsRepo.save(gs1);
//		gameStatsRepo.save(gs2);
//
//		User user02 = userRepo.save(new User("fasfajisoaisf", "fasfajisoaisf@gmail.com", "PASSWORD123", "GUEST"));
//
//		CurrentState cs0201 = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR, State.completed, user02);
//		CurrentState cs0202 = new CurrentState(10, State.completed, user02);
//
//		List<CurrentState> csList02 = new ArrayList<>();
//		csList01.add(cs0201);
//		csList02.add(cs0202);
//
//		GameStats gs3 =  new GameStats(10,9,10, user02, cs0201);
//		GameStats gs4 =  new GameStats(1,2,1, user02, cs0202);
//		List<GameStats> gsList02 = new ArrayList<>();
//		gsList02.add(gs3);
//		gsList02.add(gs4);
//
//		user02.setCurrentState(csList02);
//		user02.setGameStats(gsList02);
//		stateRepo.save(cs0201);
//		stateRepo.save(cs0202);
//		gameStatsRepo.save(gs3);
//		gameStatsRepo.save(gs4);
//
//		User user03 = userRepo.save(new User("asf3fq3", "asf3fq3@gmail.com", "PASSWORD12345", "GUEST"));
//
//		CurrentState cs0301 = new CurrentState(CONSTANTVARIABLES.DEFAULTYEAR, State.completed, user03);
//
//		List<CurrentState> csList03 = new ArrayList<>();
//		csList03.add(cs0301);
//
//		GameStats gs5 =  new GameStats(10,9,8, user03, cs0301);
//		List<GameStats> gsList03 = new ArrayList<>();
//		gsList03.add(gs5);
//
//		user03.setCurrentState(csList03);
//		user03.setGameStats(gsList03);
//		stateRepo.save(cs0301);
//		gameStatsRepo.save(gs5);
//
//		 System.out.println(ck.getCurrentState() + "" + ck.getId());
//		 System.out.println(kami.getCurrentState() + "" +  kami.getId());

	}

}
