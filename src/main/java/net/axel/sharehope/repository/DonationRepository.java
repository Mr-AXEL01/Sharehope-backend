package net.axel.sharehope.repository;

import net.axel.sharehope.domain.entities.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}