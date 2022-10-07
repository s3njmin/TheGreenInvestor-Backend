package G2T6.G2T6.G2T6.stats;

import G2T6.G2T6.G2T6.StateMachine.StateNotFoundException;
import G2T6.G2T6.G2T6.StateMachine.StateRepository;
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
    private StateRepository stateRepo;

    @Autowired
    public GameStatsController(GameStatsRepository gameStateRepo, StateRepository stateRepo){
        this.gameStateRepo = gameStateRepo;
        this.stateRepo = stateRepo;
    }

    @GetMapping("/id/{sessionId}/gameStats")
    public List<GameStats> getAllGameStats(@PathVariable (value = "sessionId") Long sessionId){
        if(!stateRepo.existsById(sessionId)){
            throw new StateNotFoundException(sessionId);
        }
        return gameStateRepo.findByPlayerCurrentStateId(sessionId);
    }

    @GetMapping("/id/{sessionId}/gameStats/{id}")
    public Optional<GameStats> getGameStats(@PathVariable (value = "sessionId") Long sessionId,
                                            @PathVariable (value = "id") Long id){
        if(!stateRepo.existsById(sessionId)){
            throw new StateNotFoundException(sessionId);
        }
        return gameStateRepo.findByIdAndPlayerCurrentStateId(id, sessionId);
    }

    @PostMapping("/id/{sessionId}/gameStats")
    public GameStats addGameStats(@PathVariable (value = "sessionId") Long sessionId, @Valid @RequestBody GameStats gameStats){
        return stateRepo.findById(sessionId).map(session ->{
            gameStats.setPlayerCurrentState(session);
            return gameStateRepo.save(gameStats);
        }).orElseThrow(() -> new StateNotFoundException(sessionId));
    }

    @PutMapping("/id/{sessionId}/gameStats/{id}")
    public GameStats updateGameStats(
            @PathVariable (value = "sessionId") Long sessionId,
            @PathVariable (value = "id") Long id,
            @Valid @RequestBody GameStats newStats){

        if(!stateRepo.existsById(sessionId)){
            throw new StateNotFoundException(sessionId);
        }
        return gameStateRepo.findByIdAndPlayerCurrentStateId(id, sessionId).map(gameStats ->{
            gameStats.setIncomeVal(newStats.getIncomeVal());
            gameStats.setEmissionVal(newStats.getEmissionVal());
            gameStats.setMoraleVal(newStats.getMoraleVal());
            return gameStateRepo.save(gameStats);
        }).orElseThrow(() -> new GameStatsNotFoundException(id));
    }

    @DeleteMapping("/id/{sessionId}/gameStats/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable (value = "sessionId") Long sessionId,
                                        @PathVariable (value = "id") Long id){

        if(!stateRepo.existsById(sessionId)){
            throw new StateNotFoundException(sessionId);
        }
        return gameStateRepo.findByIdAndPlayerCurrentStateId(id, sessionId).map(gameStats -> {
            gameStateRepo.delete(gameStats);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new GameStatsNotFoundException(id));
    }
}
