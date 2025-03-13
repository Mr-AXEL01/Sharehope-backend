package net.axel.sharehope.repository;

import net.axel.sharehope.domain.entities.Donation;
import net.axel.sharehope.security.domain.entity.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    Page<Donation> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Donation> findAllByUserOrderByCreatedAtDesc(AppUser user, Pageable pageable);

}