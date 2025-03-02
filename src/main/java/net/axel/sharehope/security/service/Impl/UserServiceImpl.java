package net.axel.sharehope.security.service.Impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.mapper.AppUserMapper;
import net.axel.sharehope.security.domain.dto.role.RoleResponseDTO;
import net.axel.sharehope.security.domain.dto.user.UserRegisterDTO;
import net.axel.sharehope.security.domain.dto.user.UserResponseDTO;
import net.axel.sharehope.security.domain.entity.AppRole;
import net.axel.sharehope.security.domain.entity.AppUser;
import net.axel.sharehope.security.repository.AppUserRepository;
import net.axel.sharehope.security.service.RoleService;
import net.axel.sharehope.security.service.UserService;
import net.axel.sharehope.service.AttachmentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepository repository;
    private final AppUserMapper mapper;
    private final RoleService roleService;
    private final AttachmentService attachmentService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponseDTO register(UserRegisterDTO registerDTO) {
        String username = generateUsername(registerDTO.email());
        String password = passwordEncoder.encode(registerDTO.password());

        Set<AppRole> roles = new HashSet<>();
        AppRole defaultRole = roleService.findByRole("ROLE_USER");
        roles.add(defaultRole);

        AppUser newUser = AppUser.register(username, registerDTO.email(), password, registerDTO.phone(), roles);
        AppUser savedUser = repository.save(newUser);

//        TODO: handel the avatar for the new user.

        return mapper.toResponse(savedUser);
    }

    private String generateUsername(String email) {
        String baseUsername = email.split("@")[0];
        String generatedUsername = baseUsername;
        int suffix = 1;

        while (repository.findByUsername(generatedUsername).isPresent()) {
            generatedUsername = baseUsername + "_" + suffix++;
        }
        return generatedUsername;
    }
}
