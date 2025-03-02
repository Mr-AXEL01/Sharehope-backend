package net.axel.sharehope.mapper;

import net.axel.sharehope.security.domain.dto.role.RoleRequestDTO;
import net.axel.sharehope.security.domain.dto.role.RoleResponseDTO;
import net.axel.sharehope.security.domain.entity.AppRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppRoleMapper extends BaseMapper<AppRole, RoleRequestDTO, RoleResponseDTO>{
}
