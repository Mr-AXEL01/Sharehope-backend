package net.axel.sharehope.security.service;

import net.axel.sharehope.security.domain.dto.user.UserRegisterDTO;
import net.axel.sharehope.security.domain.dto.user.UserResponseDTO;

public interface UserService {

    UserResponseDTO register(UserRegisterDTO registerDTO);
}
