package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Role;

import java.util.HashSet;
import java.util.Set;


public interface RoleRepository extends JpaRepository<Role, Long> {
        Set<Role> getAuthorityById(Long id);
}