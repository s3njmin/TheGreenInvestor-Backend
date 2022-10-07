package G2T6.G2T6.G2T6.StateMachine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<PlayerCurrentState, Long> {

}
