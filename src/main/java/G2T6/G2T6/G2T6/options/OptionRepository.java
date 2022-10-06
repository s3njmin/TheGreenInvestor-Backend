package G2T6.G2T6.G2T6.options;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository <Option, Long> {

    List<Option> findByOption(String option);
}

