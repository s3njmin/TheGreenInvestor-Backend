package G2T6.G2T6.G2T6.stats;

import java.util.List;
import java.util.Optional;

public interface GameStatsRepository {

    Long save(GameStats gameStats);
    int Update(GameStats gameStats);
    int deleteById(Long id);
    List<GameStats> findAll();
    Optional<GameStats> findById(Long id);

}
