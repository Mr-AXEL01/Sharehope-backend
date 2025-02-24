package net.axel.sharehope.security.repository;

import net.axel.sharehope.security.domain.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    Optional<AppRole> findByRole(String role);
}