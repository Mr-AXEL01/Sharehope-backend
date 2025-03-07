package net.axel.sharehope.domain.entities;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "filepath", nullable = false)
    private String filePath;

    @Column(name = "filetype")
    private String fileType;

    @Column(name = "uploaddate")
    private Instant uploadDate;

    @Column(name = "attachableid")
    private Long attachableId;

    @Column(name = "attachabletype")
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
