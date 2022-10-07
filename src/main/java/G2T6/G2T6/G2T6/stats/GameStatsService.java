package G2T6.G2T6.G2T6.stats;

import java.util.List;

public interface GameStatsService {
    List<GameStats> listGameStats();
    GameStats getGameStats(Long id);
    GameStats addGameStats(GameStats stats);
    GameStats updateGameStats(Long id, GameStats stats);
    void deleteGameStats(Long id);
    void factoryReset(Long id);
}
