package net.axel.sharehope.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "attachments")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    private String fileType;

    private Instant uploadDate;

    private Long attachableId;

    private String attachableType;

    public static Attachment createAttachment(String filePath, String fileType, Long attachableId, String attachableType) {
        Attachment attachment = new Attachment();
        attachment.filePath = filePath;
        attachment.fileType = fileType;
        attachment.uploadDate = Instant.now();
        attachment.attachableId = attachableId;
        attachment.attachableType = attachableType;
        return attachment;
    }
}
