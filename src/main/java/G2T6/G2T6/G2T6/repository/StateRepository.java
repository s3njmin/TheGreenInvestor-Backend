package G2T6.G2T6.G2T6.repository;

import G2T6.G2T6.G2T6.models.CurrentState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<CurrentState, Long> {
    List<CurrentState> findByCurrentState(String currentState);
}
