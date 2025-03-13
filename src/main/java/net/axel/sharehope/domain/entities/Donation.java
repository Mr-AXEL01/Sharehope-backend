package net.axel.sharehope.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.axel.sharehope.domain.enums.DonationStatus;
import net.axel.sharehope.security.domain.entity.AppUser;

import java.time.Instant;

@Entity
@Table(name = "donations")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Donation extends Action {

    @Enumerated(EnumType.STRING)
    private DonationStatus donationStatus;

    public static Donation createDonation(Double amount, String description, Category category, AppUser user) {
        Donation donation = new Donation();
        donation.setAmount(amount)
                .setDescription(description)
                .setCategory(category)
                .setUser(user)
                .setCreatedAt(Instant.now());
        donation.donationStatus = DonationStatus.PENDING;
        return donation;
    }

    public void updateDonation(String description, Category category) {
        this.setDescription(description);
        this.setCategory(category);
    }
}
