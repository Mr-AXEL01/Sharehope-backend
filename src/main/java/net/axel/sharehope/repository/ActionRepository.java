package net.axel.sharehope.repository;

import net.axel.sharehope.domain.entities.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
}