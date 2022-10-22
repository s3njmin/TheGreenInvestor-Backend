package G2T6.G2T6.G2T6.repository;

import G2T6.G2T6.G2T6.models.GameStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameStatsRepository extends JpaRepository<GameStats, Long> {
    List<GameStats> findByUserId(Long user);
    Optional<GameStats> findByIdAndUserId(Long id, Long user);
//    Optional<GameStats> getAllGameStatsOrderBySumEmissionVal(Long count);
}
// ID  	EMISSION_VAL  	INCOME_VAL  	MORALE_VAL  	CURRENT_STATE_ID
