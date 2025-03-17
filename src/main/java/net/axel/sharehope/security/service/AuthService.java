package net.axel.sharehope.security.service;

import net.axel.sharehope.security.domain.dto.user.response.AuthenticationResponseDTO;
import net.axel.sharehope.security.domain.dto.user.requests.UserLoginDTO;
import net.axel.sharehope.security.domain.dto.user.requests.UserRegisterDTO;
import net.axel.sharehope.security.domain.dto.user.response.UserResponseDTO;

public interface AuthService {

    UserResponseDTO register(UserRegisterDTO registerDTO);

    AuthenticationResponseDTO login(UserLoginDTO loginDTO);
}
