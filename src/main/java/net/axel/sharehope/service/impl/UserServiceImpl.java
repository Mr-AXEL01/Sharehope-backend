package net.axel.sharehope.service.impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.attachment.AttachmentRequestDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentResponseDTO;
import net.axel.sharehope.domain.entities.Attachment;
import net.axel.sharehope.exception.domains.ResourceNotFoundException;
import net.axel.sharehope.mapper.UserMapper;
import net.axel.sharehope.security.domain.dto.user.UserResponseDTO;
import net.axel.sharehope.security.domain.dto.user.UserUpdateDTO;
import net.axel.sharehope.security.domain.entity.AppUser;
import net.axel.sharehope.security.repository.AppUserRepository;
import net.axel.sharehope.security.service.Impl.JWTService;
import net.axel.sharehope.security.service.RoleService;
import net.axel.sharehope.service.UserService;
import net.axel.sharehope.service.AttachmentService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final AuthenticationManager authManager;
    private final JWTService jwtService;


    @Override
    public UserResponseDTO findById(Long id) {
        AppUser user = getUser(id);

        String avatar = attachmentService.findAttachmentUrl("AppUser", user.getId());
        user.setAvatar(avatar != null ? avatar : DEFAULT_AVATAR_URL);

        return mapper.mapToResponseDTO(user);
    }

    @Override
    public AppUser findUserEntity(String username) {
        AppUser user = repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find the user with username: " + username));

        String avatar = attachmentService.findAttachmentUrl("AppUser", user.getId());
        user.setAvatar(avatar != null ? avatar : DEFAULT_AVATAR_URL);

        return user;
    }

    @Override
    public UserResponseDTO updateAvatar(Long id, MultipartFile avatarUrl) {
        AppUser user = getUser(id);
        Attachment existingAttachment = attachmentService.findAttachment("AppUser", user.getId());
        if (existingAttachment != null) {
            attachmentService.deleteAttachment(existingAttachment.getId(), "users/avatars");
        }
        String avatarPath;
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            AttachmentResponseDTO newAvatar = attachmentService.createAttachment(new AttachmentRequestDTO(
                            avatarUrl,
                            "AppUser",
                            user.getId(),
                            "users/avatars"
                    )
            );
            avatarPath = newAvatar.filePath();
        } else {
            avatarPath = DEFAULT_AVATAR_URL;
        }
        user.setAvatar(avatarPath);
        return mapper.mapToResponseDTO(user);
    }

    @Override
    public UserResponseDTO update(Long id, UserUpdateDTO updateDTO) {
        return null;
    }

    @Override
    public UserResponseDTO updatePassword(Long id, String password) {
        return null;
    }

    private AppUser getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find the user with id: " + id));
    }
}
