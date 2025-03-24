package net.axel.sharehope.repository;

import net.axel.sharehope.domain.entities.Need;
import net.axel.sharehope.security.domain.entity.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NeedRepository extends JpaRepository<Need, Long> {

    Page<Need> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Need> findAllByUserOrderByCreatedAtDesc(AppUser user, Pageable pageable);
}