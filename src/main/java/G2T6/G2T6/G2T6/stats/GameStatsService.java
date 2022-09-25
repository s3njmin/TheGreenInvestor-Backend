package G2T6.G2T6.G2T6.stats;

import java.util.List;

public interface GameStatsService {
    List<GameStats> listGameStats();
    GameStats getGameStats(Long id);
    GameStats addGameStats(GameStats book);
    GameStats updateGameStats(Long id, GameStats book);
    int deleteGameStats(Long id);
}
