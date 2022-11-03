package G2T6.G2T6.G2T6.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import G2T6.G2T6.G2T6.models.orders.QuestionOrder;

@Repository
public interface QuestionOrderRepository extends JpaRepository<QuestionOrder, Long> {
    Optional<QuestionOrder> findById(final Long id);
}
