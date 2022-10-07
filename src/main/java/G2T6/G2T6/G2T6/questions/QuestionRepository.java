package G2T6.G2T6.G2T6.questions;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository <Question, Long> {

    List<Question> findByQuestion(String question);
    Optional<Question> findById(Long id);
}
