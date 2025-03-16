package net.axel.sharehope.service;

import net.axel.sharehope.security.domain.dto.user.UserResponseDTO;
import net.axel.sharehope.security.domain.dto.user.UserUpdateDTO;
import net.axel.sharehope.security.domain.entity.AppUser;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserResponseDTO findById(Long id);

    AppUser findUserEntity(String username);

    UserResponseDTO updateAvatar(Long id, MultipartFile avatarUrl);

    UserResponseDTO update(Long id, UserUpdateDTO updateDTO);

    UserResponseDTO updatePassword(Long id, String password);
}
