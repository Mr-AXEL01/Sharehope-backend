package net.axel.sharehope.repository;

import net.axel.sharehope.domain.entities.Need;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NeedRepository extends JpaRepository<Need, Long> {
}