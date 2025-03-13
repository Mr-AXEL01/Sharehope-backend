package net.axel.sharehope.web;

import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.action.ActionCreateDTO;
import net.axel.sharehope.domain.dtos.action.ActionStatusDTO;
import net.axel.sharehope.domain.dtos.action.ActionUpdateDTO;
import net.axel.sharehope.domain.dtos.action.donation.DonationResponseDTO;
import net.axel.sharehope.service.DonationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(DonationController.CONTROLLER_PATH)

@RequiredArgsConstructor
public class DonationController {

    public final static String CONTROLLER_PATH = "api/v1/donations";

    private final DonationService service;

    @PostMapping
    public ResponseEntity<DonationResponseDTO> create(@Valid @ModelAttribute ActionCreateDTO requestDTO) throws StripeException {
        DonationResponseDTO donation = service.createDonation(requestDTO);
        return new ResponseEntity<>(donation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonationResponseDTO> update(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute ActionUpdateDTO dto
    ) {
        DonationResponseDTO updatedDonation = service.update(id, dto);
        return ResponseEntity.ok(updatedDonation);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DonationResponseDTO> updateStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody ActionStatusDTO statusDTO
    ) {
        DonationResponseDTO updatedDonation = service.updateStatus(id, statusDTO);
        return ResponseEntity.ok(updatedDonation);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DonationResponseDTO> findById(@PathVariable("id") Long id) {
        DonationResponseDTO donation = service.findById(id);
        return new ResponseEntity<>(donation, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<DonationResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<DonationResponseDTO> donations = service.findAll(page, size);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/myDonations")
    public ResponseEntity<List<DonationResponseDTO>> findAllMyDonations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<DonationResponseDTO> donations = service.findAllMyDonation(page, size);
        return ResponseEntity.ok(donations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
