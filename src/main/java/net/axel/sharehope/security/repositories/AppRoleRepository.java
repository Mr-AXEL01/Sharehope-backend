package net.axel.sharehope.security.repositories;

import net.axel.sharehope.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppRoleRepository extends JpaRepository<AppRole, UUID> {
}