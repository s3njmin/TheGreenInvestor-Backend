package G2T6.G2T6.G2T6.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import G2T6.G2T6.G2T6.models.random.OptionOrder;

@Repository
public interface OptionOrderRepository extends JpaRepository<OptionOrder, Long> {
    Optional<OptionOrder> findById(final Long id);
}
