package G2T6.G2T6.G2T6.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class GameStatsController {
    private GameStatsService gameStatsService;

    @Autowired
    public GameStatsController(GameStatsService gs){
        this.gameStatsService = gs;
    }

    @GetMapping("/gameStats")
    public List<GameStats> getAllGameStats(){
        return gameStatsService.listGameStats();
    }

    @GetMapping("/gameStats/{id}")
    public GameStats getGameStats(@PathVariable Long id){
        GameStats gameStats = gameStatsService.getGameStats(id);
        if(gameStats == null)  throw new GameStatsNotFoundException(id);
        return gameStats;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/gameStats")
    public GameStats addGameStats(@Valid @RequestBody GameStats gameStats){
        return gameStatsService.addGameStats(gameStats);
    }

    @PutMapping("/gameStats/{id}")
    public GameStats updateGameStats(@PathVariable Long id, @Valid @RequestBody GameStats newStats){
        GameStats gameStats = gameStatsService.updateGameStats(id, newStats);
        if(gameStats == null)  throw new GameStatsNotFoundException(id);
        return gameStats;
    }

    @DeleteMapping("/gameStats/{id}")
    public void deleteBook(@PathVariable Long id){
        try{
            gameStatsService.deleteGameStats(id);
        }catch (EmptyResultDataAccessException e){
            throw new GameStatsNotFoundException(id);
        }
    }
}
