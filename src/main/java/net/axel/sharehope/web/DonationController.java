package net.axel.sharehope.web;

import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.action.ActionRequestDTO;
import net.axel.sharehope.domain.dtos.action.donation.DonationResponseDTO;
import net.axel.sharehope.service.DonationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(DonationController.CONTROLLER_PATH)

@RequiredArgsConstructor
public class DonationController {

    public final static String CONTROLLER_PATH = "api/v1/donations";

    private final DonationService service;

    @PostMapping
    public ResponseEntity<DonationResponseDTO> create(@Valid @ModelAttribute ActionRequestDTO requestDTO) throws StripeException {
        DonationResponseDTO donation = service.createDonation(requestDTO);
        return new ResponseEntity<>(donation, HttpStatus.CREATED);
    }
}
