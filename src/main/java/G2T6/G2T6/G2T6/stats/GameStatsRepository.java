package G2T6.G2T6.G2T6.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameStatsRepository extends JpaRepository<GameStats, Long> {


}
