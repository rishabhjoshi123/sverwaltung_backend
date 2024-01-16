package ch.rishabh.sverwaltung.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.rishabh.sverwaltung.model.ERole;
import ch.rishabh.sverwaltung.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
