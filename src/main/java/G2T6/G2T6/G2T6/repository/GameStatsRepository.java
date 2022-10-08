package G2T6.G2T6.G2T6.repository;

import G2T6.G2T6.G2T6.models.GameStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameStatsRepository extends JpaRepository<GameStats, Long> {
    List<GameStats> findByCurrentStateId(Long sessionId);
    Optional<GameStats> findByIdAndCurrentStateId(Long id, Long sessionId);
}
