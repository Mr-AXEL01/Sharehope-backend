package net.axel.sharehope.mapper;

import net.axel.sharehope.security.domain.dto.user.UserRegisterDTO;
import net.axel.sharehope.security.domain.dto.user.UserResponseDTO;
import net.axel.sharehope.security.domain.entity.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper extends BaseMapper<AppUser, UserRegisterDTO, UserResponseDTO> {
}
