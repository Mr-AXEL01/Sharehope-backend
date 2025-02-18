package net.axel.sharehope.mapper;

import net.axel.sharehope.domain.dtos.action.ActionRequestDTO;
import net.axel.sharehope.domain.entities.Donation;
import net.axel.sharehope.domain.entities.Need;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActionMapper {

    Need toNeedEntity(ActionRequestDTO actionRequestDTO);

    Donation toDonationEntity(ActionRequestDTO actionRequestDTO);
}
