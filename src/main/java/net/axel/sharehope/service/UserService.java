package net.axel.sharehope.service;

import net.axel.sharehope.security.domain.dto.user.requests.UserPasswordUpdateDTO;
import net.axel.sharehope.security.domain.dto.user.response.UserAuthResponseDTO;
import net.axel.sharehope.security.domain.dto.user.response.UserResponseDTO;
import net.axel.sharehope.security.domain.dto.user.requests.UserUpdateDTO;
import net.axel.sharehope.security.domain.entity.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserResponseDTO findById(Long id);

    UserResponseDTO findByUsername(String username);

    AppUser findUserEntity(String username);

    UserResponseDTO updateAvatar(Long id, MultipartFile avatarUrl);

    UserAuthResponseDTO update(Long id, UserUpdateDTO updateDTO);

    UserResponseDTO updatePassword(Long id, UserPasswordUpdateDTO passwordUpdateDTO);

    Page<UserResponseDTO> getAllUsers(int page, int size);
}
