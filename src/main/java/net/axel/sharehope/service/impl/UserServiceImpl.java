package net.axel.sharehope.service.impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.domain.dtos.attachment.AttachmentRequestDTO;
import net.axel.sharehope.domain.dtos.attachment.AttachmentResponseDTO;
import net.axel.sharehope.domain.entities.Attachment;
import net.axel.sharehope.exception.domains.ResourceNotFoundException;
import net.axel.sharehope.mapper.UserMapper;
import net.axel.sharehope.security.domain.dto.user.requests.UserPasswordUpdateDTO;
import net.axel.sharehope.security.domain.dto.user.response.UserAuthResponseDTO;
import net.axel.sharehope.security.domain.dto.user.response.UserResponseDTO;
import net.axel.sharehope.security.domain.dto.user.requests.UserUpdateDTO;
import net.axel.sharehope.security.domain.entity.AppUser;
import net.axel.sharehope.security.repository.AppUserRepository;
import net.axel.sharehope.security.service.Impl.JWTService;
import net.axel.sharehope.security.service.RoleService;
import net.axel.sharehope.service.UserService;
import net.axel.sharehope.service.AttachmentService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public UserResponseDTO findByUsername(String username) {
        return mapper.mapToResponseDTO(findUserEntity(username));
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
    public UserResponseDTO updateAvatar(Long id, MultipartFile avatar) {
        AppUser user = getUser(id);
        Attachment existingAttachment = attachmentService.findAttachment("AppUser", user.getId());
        if (existingAttachment != null) {
            attachmentService.deleteAttachment(existingAttachment.getId(), "users/avatars/");
        }
        String avatarPath;
        if (avatar != null && !avatar.isEmpty()) {
            AttachmentResponseDTO newAvatar = attachmentService.createAttachment(new AttachmentRequestDTO(
                            avatar,
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
    public UserAuthResponseDTO update(Long id, UserUpdateDTO updateDTO) {
        AppUser user = getUser(id);
        user.updateUser(updateDTO);

        String avatar = (getAvatar(user.getId()) != null)
                ? getAvatar(user.getId()).getFilePath()
                : DEFAULT_AVATAR_URL;
        user.setAvatar(avatar);

        String newToken = jwtService.generateToken(user);

        return mapper.mapToAuthResponseDTO(user, newToken);
    }

    @Override
    public UserResponseDTO updatePassword(Long id, UserPasswordUpdateDTO passwordDTO) {
        AppUser user = getUser(id);

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!user.getUsername().equals(currentUsername)) {
            throw new IllegalArgumentException("You can only update your own password.");
        }

        if (!passwordEncoder.matches(passwordDTO.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }

        String encodedPassword = passwordEncoder.encode(passwordDTO.newPassword());
        user.setPassword(encodedPassword);

        return mapper.mapToResponseDTO(user);
    }

    private AppUser getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find the user with id: " + id));
    }

    private Attachment getAvatar(Long entityId) {
        return attachmentService.findAttachment("AppUser", entityId);
    }

}
