package net.axel.sharehope.service.impl;

import lombok.RequiredArgsConstructor;
import net.axel.sharehope.exception.domains.ResourceNotFoundException;
import net.axel.sharehope.mapper.UserMapper;
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
    public AppUser findUserEntity(String username) {
        AppUser user = repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find the user with username: " + username));

        String avatar = attachmentService.findAttachmentUrl("AppUser", user.getId());
        user.setAvatar(avatar != null ? avatar : DEFAULT_AVATAR_URL);

        return user;
    }
}
