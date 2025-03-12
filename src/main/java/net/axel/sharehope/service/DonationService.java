package net.axel.sharehope.service;

import com.stripe.exception.StripeException;
import net.axel.sharehope.domain.dtos.action.ActionRequestDTO;
import net.axel.sharehope.domain.dtos.action.donation.DonationResponseDTO;

public interface DonationService {

    DonationResponseDTO createDonation(ActionRequestDTO dto) throws StripeException;
}
