package net.axel.sharehope.security.service.Impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.exception.domains.ResourceNotFoundException;
import net.axel.sharehope.mapper.AppRoleMapper;
import net.axel.sharehope.security.domain.dto.role.RoleResponseDTO;
import net.axel.sharehope.security.domain.entity.AppRole;
import net.axel.sharehope.security.repository.AppRoleRepository;
import net.axel.sharehope.security.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final AppRoleRepository repository;
    private final AppRoleMapper mapper;

    @Override
    public AppRole findByRole(String role) {
        return repository.findByRole(role)
                .orElseThrow(() -> new ResourceNotFoundException("AppRole", role));
    }
}
