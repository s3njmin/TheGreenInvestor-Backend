package G2T6.G2T6.G2T6.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameStatsServiceImplementation implements GameStatsService {
    private GameStatsRepository gameStatsRepository;

    @Autowired
    public GameStatsServiceImplementation(GameStatsRepository gameStatsRepository){
        this.gameStatsRepository = gameStatsRepository;
    }

    @Override
    public List<GameStats> listGameStats() {
        return gameStatsRepository.findAll();
    }

    @Override
    public GameStats getGameStats(Long id) {
        return gameStatsRepository.findById(id).orElse(null);
    }

    @Override
    public GameStats addGameStats(GameStats gameStats) {
        return gameStatsRepository.save(gameStats);
    }

    @Override
    public GameStats updateGameStats(Long id, GameStats gameStats) {
        return gameStatsRepository.findById(id).map(gameStat -> {
            gameStat.setEmissionVal(gameStats.getEmissionVal());
            gameStat.setIncomeVal(gameStats.getIncomeVal());
            gameStat.setMoraleVal(gameStats.getMoraleVal());
            return gameStatsRepository.save(gameStat);
        }).orElse(null);
     }

    @Override
    public void deleteGameStats(Long id) {
        gameStatsRepository.deleteById(id);
    }
}
