package net.axel.sharehope.repository;

import net.axel.sharehope.domain.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}