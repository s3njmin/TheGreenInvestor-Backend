package G2T6.G2T6.G2T6.stats;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGameStatsRepository implements GameStatsService{

    @Override
    public List<GameStats> listGameStats() {
        return null;
    }

    @Override
    public GameStats getGameStats(Long id) {
        return null;
    }

    @Override
    public GameStats addGameStats(GameStats book) {
        return null;
    }

    @Override
    public GameStats updateGameStats(Long id, GameStats book) {
        return null;
    }

    @Override
    public void deleteGameStats(Long id) {

    }
}
