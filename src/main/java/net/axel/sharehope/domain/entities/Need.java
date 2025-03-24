package net.axel.sharehope.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.axel.sharehope.domain.dtos.action.ActionStatusDTO;
import net.axel.sharehope.domain.enums.DonationStatus;
import net.axel.sharehope.domain.enums.NeedStatus;
import net.axel.sharehope.security.domain.entity.AppUser;

import java.time.Instant;

@Entity
@Table(name = "needs")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Need extends Action {

    @Enumerated(EnumType.STRING)
    private NeedStatus needStatus;

    public static Need createNeed(Double amount, String description, Category category, AppUser user) {
        Need donation = new Need();
        donation.setAmount(amount)
                .setDescription(description)
                .setCategory(category)
                .setUser(user)
                .setCreatedAt(Instant.now());
        donation.needStatus = NeedStatus.PENDING;
        return donation;
    }

    public void updateNeed(String description, Category category) {
        this.setDescription(description);
        this.setCategory(category);
    }

    public void updateStatus(ActionStatusDTO statusDTO) {
        switch (statusDTO.status()) {
            case "PENDING" -> this.needStatus = NeedStatus.PENDING;
            case "CONFIRMED" -> this.needStatus = NeedStatus.CONFIRMED;
            case "REJECTED" -> this.needStatus = NeedStatus.REJECTED;
            default -> throw new IllegalArgumentException("Invalid need status");
        }
    }
}
