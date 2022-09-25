package G2T6.G2T6.G2T6.stats;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameStatsServiceImplementation implements GameStatsService {
    private GameStatsRepository gameStatsRepository;
    public GameStatsServiceImplementation(GameStatsRepository gameStatsRepository){

        this.gameStatsRepository = gameStatsRepository;

    }
    @Override
    public List<GameStats> listGameStats() {
        return gameStatsRepository.findAll();
    }

    @Override
    public GameStats getGameStats(Long id) {
        Optional<GameStats> b = gameStatsRepository.findById(id);
        if (b.isPresent())
            return b.get();
        else
            return null;

    }

    @Override
    public GameStats addGameStats(GameStats gameStats) {
        gameStats.setId(gameStatsRepository.save(gameStats));
        return gameStats;
    }

    @Override
    public GameStats updateGameStats(Long id, GameStats gameStats) {
        GameStats gs = gameStats;
        gs.setId(id);
        if(gameStatsRepository.Update(gameStats) > 0 ){
            return gs;
        }else {
            return null;
        }
     }

    @Override
    public int deleteGameStats(Long id) {
        return gameStatsRepository.deleteById(id);
    }
}
