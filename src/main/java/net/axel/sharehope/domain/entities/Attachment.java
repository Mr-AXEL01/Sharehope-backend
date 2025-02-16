package net.axel.sharehope.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    private String fileType;

    private LocalDateTime uploadDate;

    private Long attachableId;

    private String attachableType;
}
