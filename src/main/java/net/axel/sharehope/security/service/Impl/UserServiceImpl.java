package net.axel.sharehope.security.service.Impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.attachment.AttachmentRequestDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentResponseDTO;
import net.axel.sharehope.mapper.UserMapper;
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

    private static final String DEFAULT_AVATAR_URL = "https://res.cloudinary.com/dofubyjcd/image/upload/v1741048199/shareHope/users/DefaultAvatar.jpg";

    private final AppUserRepository repository;
    private final UserMapper mapper;
    private final RoleService roleService;
    private final AttachmentService attachmentService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponseDTO register(UserRegisterDTO registerDTO) {
        String username = generateUsername(registerDTO.email());
        String password = passwordEncoder.encode(registerDTO.password());

        AppRole defaultRole = roleService.findByRole("ROLE_USER");
        Set<AppRole> roles = Set.of(defaultRole);

        AppUser newUser = AppUser.register(username, registerDTO.email(), password, registerDTO.phone(), roles);
        AppUser savedUser = repository.save(newUser);

        if (registerDTO.avatar() != null && !registerDTO.avatar().isEmpty()) {
            AttachmentRequestDTO requestDTO = new AttachmentRequestDTO(
                    registerDTO.avatar(),
                    "AppUser",
                    savedUser.getId(),
                    "users/avatars"
            );
            AttachmentResponseDTO Attachment = attachmentService.createAttachment(requestDTO);
            savedUser.setAvatar(Attachment.filePath());
        }else {
            savedUser.setAvatar(DEFAULT_AVATAR_URL);
        }

        return mapper.mapToResponseDTO(savedUser);
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
