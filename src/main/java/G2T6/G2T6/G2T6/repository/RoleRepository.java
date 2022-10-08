package G2T6.G2T6.G2T6.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import G2T6.G2T6.G2T6.models.security.ERole;
import G2T6.G2T6.G2T6.models.security.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
