package net.axel.sharehope.repository;

import net.axel.sharehope.domain.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    Optional<Attachment> findByAttachableTypeAndAttachableId(String attachableType, Long attachableId);
}