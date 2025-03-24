package net.axel.sharehope.service;

import com.stripe.exception.StripeException;
import net.axel.sharehope.domain.dtos.action.ActionCreateDTO;
import net.axel.sharehope.domain.dtos.action.ActionStatusDTO;
import net.axel.sharehope.domain.dtos.action.ActionUpdateDTO;
import net.axel.sharehope.domain.dtos.action.donation.DonationResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DonationService {

    DonationResponseDTO createDonation(ActionCreateDTO dto) throws StripeException;

    DonationResponseDTO findById(Long id);

    Page<DonationResponseDTO> findAll(int page, int size);

    List<DonationResponseDTO> findAllMyDonation(int page, int size);

    DonationResponseDTO update(Long id, ActionUpdateDTO dto);

    DonationResponseDTO updateStatus(Long id, ActionStatusDTO statusDTO);

    void delete(Long id);
}
