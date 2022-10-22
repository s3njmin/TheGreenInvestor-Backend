package G2T6.G2T6.G2T6.controllers;

import G2T6.G2T6.G2T6.exceptions.GameStatsNotFoundException;
import G2T6.G2T6.G2T6.exceptions.NotEnoughGameStatsException;
import G2T6.G2T6.G2T6.exceptions.StateNotFoundException;
import G2T6.G2T6.G2T6.exceptions.UserNotFoundException;
import G2T6.G2T6.G2T6.misc.State;
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

    @GetMapping("/gameStats")
    public List<GameStats> getAllGameStats(){
        return gameStateRepo.findAll();
    }

    @GetMapping("/gameStats/{count}")
    public List<GameStats> getAllTopNGameStats(@PathVariable (value = "count") Long count){
        List<GameStats> selectedStats = gameStateRepo.findAll();
        System.out.println("Correct output");
        if( count >= selectedStats.size()) return null; // change to error later
        for(GameStats gs: selectedStats){
            if(gs.getCurrentState().getCurrentState() != State.completed){
                selectedStats.remove(gs);
            }
        }

        if( count >= selectedStats.size()) throw new NotEnoughGameStatsException(count); // change to error later

        Collections.sort(selectedStats);
        List<GameStats> topNStats = new ArrayList<>();
        for(int i = 0; i < count; i++){
            topNStats.add(selectedStats.get(i));
        }
        return  topNStats;
    }

    @GetMapping("/id/{userId}/gameStats")
    public List<GameStats> getAllSelectedUserGameStats(@PathVariable (value = "userId") Long userId){
        if(!userRepo.existsById(userId)){
            throw new UserNotFoundException(userId);
        }
        return gameStateRepo.findByUserId(userId);
    }

    @GetMapping("/id/{userId}/gameStats/{id}")
    public Optional<GameStats> getGameStats(@PathVariable (value = "userId") Long userId,
                                            @PathVariable (value = "id") Long id){
        if(!userRepo.existsById(userId)){
            throw new UserNotFoundException(userId);
        }
        return gameStateRepo.findByIdAndUserId(id, userId);
    }

    @PostMapping("/id/{userId}/gameStats")
    public GameStats addGameStats(@PathVariable (value = "userId") Long userId, @Valid @RequestBody GameStats gameStats){
        return userRepo.findById(userId).map(user ->{
            gameStats.setUser(user);
            return gameStateRepo.save(gameStats);
        }).orElseThrow(() -> new UserNotFoundException(userId));
    }

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
