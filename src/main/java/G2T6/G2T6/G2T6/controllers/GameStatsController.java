package G2T6.G2T6.G2T6.controllers;

import G2T6.G2T6.G2T6.exceptions.GameStatsNotFoundException;
import G2T6.G2T6.G2T6.exceptions.NotEnoughGameStatsException;
import G2T6.G2T6.G2T6.exceptions.StateNotFoundException;
import G2T6.G2T6.G2T6.exceptions.UserNotFoundException;
import G2T6.G2T6.G2T6.misc.State;
import G2T6.G2T6.G2T6.models.CurrentState;
import G2T6.G2T6.G2T6.repository.StateRepository;
import G2T6.G2T6.G2T6.models.GameStats;
import G2T6.G2T6.G2T6.repository.GameStatsRepository;
import G2T6.G2T6.G2T6.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class GameStatsController {
    private GameStatsRepository gameStateRepo;
    private UserRepository userRepo;

    @Autowired
    public GameStatsController(GameStatsRepository gameStateRepo, UserRepository userRepo){
        this.gameStateRepo = gameStateRepo;
        this.userRepo = userRepo;
    }

    /**
     * List all game stats in the system
     * @return list of all game stats
     */
    @GetMapping("/gameStats")
    public List<GameStats> getAllGameStats(){
        return gameStateRepo.findAll();
    }

    /**
     * List all top n game stats in the system
     * First it filter through all game stats and only select those that have completed state
     * Next we Sort them and return count number of stats in descending order
     * Remove duplicate user's stats only keep the highest
     * @param count a long value
     * @return return count number of people in term of total game stats score
     */
    @GetMapping("/gameStats/{count}")
    public List<GameStats> getAllTopNGameStats(@PathVariable (value = "count") int count){
        List<GameStats> completedStats = filterAllCompletedGameStats(getAllGameStats());

        enoughCount(count, completedStats.size());

        Collections.sort(completedStats);

        List<GameStats> completedStatsWithOutDuplicate = filterUniqueGameStats(completedStats);

        enoughCount(count, completedStats.size());

        return  completedStatsWithOutDuplicate.subList(0, count);
    }

    /**
     * check if theres enough count
     * @param expectedCount a integer value
     * @param count a integer value
     */
    public void enoughCount(int expectedCount, int count){
        if(expectedCount > count) throw new NotEnoughGameStatsException(count);
    }

    /**
     * filter to get all completed game stats
     * @param gameStats a list of GameStats object
     * @return  all completed game stats
     */
    public List<GameStats> filterAllCompletedGameStats(List<GameStats> gameStats){
        List<GameStats> completedStats = new ArrayList<>();
        for(GameStats gs: gameStats){
            if(gs.getCurrentState().getCurrentState() == State.completed){
                completedStats.add(gs);
            }
        }
        return  completedStats;
    }

    /**
     * Only keep one game stats from each user
     * @param gameStats a list of GameStats object
     * @return one game stats from each user
     */
    public List<GameStats> filterUniqueGameStats(List<GameStats> gameStats){
        List<GameStats> completedStatsWithOutDuplicate = new ArrayList<>();List<Long> userIds = new ArrayList<>();
        for(int i = 0; i < gameStats.size(); i++){
            Long userId = gameStats.get(i).getUser().getId();
            if(!userIds.contains(userId)){
                completedStatsWithOutDuplicate.add(gameStats.get(i));
                userIds.add(userId);
            }
        }
        return  completedStatsWithOutDuplicate;
    }

    /**
     * searching for all selected user's game stats
     * @param userId a long value
     * @return return all selected user's game stats
     **/
    @GetMapping("/id/{userId}/gameStats")
    public List<GameStats> getAllSelectedUserGameStats(@PathVariable (value = "userId") Long userId){
        if(!userRepo.existsById(userId)){
            throw new UserNotFoundException(userId);
        }
        return gameStateRepo.findByUserId(userId);
    }

    /**
     * Get game states of selected user and selected game stats id
     * @param userId a long value
     * @param id a long value
     * @return game states of selected user and selected game stats id
     */
    @GetMapping("/id/{userId}/gameStats/{id}")
    public Optional<GameStats> getGameStats(@PathVariable (value = "userId") Long userId,
                                            @PathVariable (value = "id") Long id){
        if(!userRepo.existsById(userId)){
            throw new UserNotFoundException(userId);
        }
        System.out.println("entered");
        return gameStateRepo.findByIdAndUserId(id, userId);
    }

    /**
     * add a new game stats to selected user
     * @param userId a long value
     * @param gameStats a GameStats object
     * @return the new game stats added to selected user
     */
    @PostMapping("/id/{userId}/gameStats")
    public GameStats addGameStats(@PathVariable (value = "userId") Long userId, @Valid @RequestBody GameStats gameStats){
        return userRepo.findById(userId).map(user ->{
            gameStats.setUser(user);
            return gameStateRepo.save(gameStats);
        }).orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * update selected users game stats with selected id
     * @param userId a long value
     * @param id a long value
     * @param newStats a GameStats object
     * @return the updated game stats
     */
    @PutMapping("/id/{userId}/gameStats/{id}")
    public GameStats updateGameStats(
            @PathVariable (value = "userId") Long userId,
            @PathVariable (value = "id") Long id,
            @Valid @RequestBody GameStats newStats){

        if(!userRepo.existsById(userId)){
            throw new UserNotFoundException(userId);
        }
        return gameStateRepo.findByIdAndUserId(id, userId).map(gameStats ->{
            gameStats.setIncomeVal(newStats.getIncomeVal());
            gameStats.setEmissionVal(newStats.getEmissionVal());
            gameStats.setMoraleVal(newStats.getMoraleVal());
            return gameStateRepo.save(gameStats);
        }).orElseThrow(() -> new GameStatsNotFoundException(id));
    }

    /**
     * delete a selected users game stats with selected id
     * @param userId a long value
     * @param id a long value
     * @return ResponseEntity of the operation
     */
    @DeleteMapping("/id/{userId}/gameStats/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable (value = "userId") Long userId,
                                        @PathVariable (value = "id") Long id){

        if(!userRepo.existsById(userId)){
            throw new UserNotFoundException(userId);
        }
        Optional<GameStats> gs = gameStateRepo.findByIdAndUserId(id, userId);
        System.out.println(gs);
        return gameStateRepo.findByIdAndUserId(id, userId).map(gameStats -> {
            // gameStateRepo.delete(gameStats);
            gameStateRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new GameStatsNotFoundException(id));
    }
}
