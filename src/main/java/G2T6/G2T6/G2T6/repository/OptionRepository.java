package G2T6.G2T6.G2T6.repository;

import java.util.List;
import java.util.Optional;

import G2T6.G2T6.G2T6.options.Option;
import G2T6.G2T6.G2T6.questions.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository <Option, Long> {
    List<Option> findByQuestionId(Long questionId);
    Optional<Option> findByIdAndQuestionId(Long id, Long questionId);
}
