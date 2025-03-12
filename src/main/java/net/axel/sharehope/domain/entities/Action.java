package net.axel.sharehope.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.axel.sharehope.security.domain.entity.AppUser;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actions")
@Inheritance(strategy = InheritanceType.JOINED)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public abstract class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private String description;

    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Transient
    private List<String> attachments = new ArrayList<>();
}
