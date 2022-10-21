package G2T6.G2T6.G2T6.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import G2T6.G2T6.G2T6.models.random.QuestionOrder;

@Repository
public interface QuestionOrderRepository extends JpaRepository<QuestionOrder, Long> {
    // ArrayList<QuestionOrder> findByIndexArray (ArrayList<Integer> indexArray);
    Optional<QuestionOrder> findById(final Long id);
}
