package net.axel.sharehope.mapper;

import net.axel.sharehope.domain.dtos.action.ActionRequestDTO;
import net.axel.sharehope.domain.dtos.action.donation.DonationResponseDTO;
import net.axel.sharehope.domain.entities.Donation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DonationMapper extends BaseMapper<Donation, ActionRequestDTO, DonationResponseDTO> {

}
