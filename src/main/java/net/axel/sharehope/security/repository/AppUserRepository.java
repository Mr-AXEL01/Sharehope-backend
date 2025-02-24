package net.axel.sharehope.security.repository;

import net.axel.sharehope.security.domain.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}