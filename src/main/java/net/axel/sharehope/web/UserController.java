package net.axel.sharehope.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.axel.sharehope.security.domain.dto.user.requests.UserPasswordUpdateDTO;
import net.axel.sharehope.security.domain.dto.user.response.UserAuthResponseDTO;
import net.axel.sharehope.security.domain.dto.user.response.UserResponseDTO;
import net.axel.sharehope.security.domain.dto.user.requests.UserUpdateDTO;
import net.axel.sharehope.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(UserController.CONTROLLER_PATH)

@RequiredArgsConstructor
public class UserController {

    public final static String CONTROLLER_PATH = "api/v1/profile";

    private final UserService service;

    @PatchMapping("/{id}/avatar")
    public ResponseEntity<UserResponseDTO> updateAvatar(@PathVariable("id") Long id, MultipartFile avatarUrl) {
        UserResponseDTO updatedUser = service.updateAvatar(id, avatarUrl);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAuthResponseDTO> update(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateDTO updateDTO) {
        UserAuthResponseDTO updatedUser = service.update(id, updateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<UserResponseDTO> updatePassword(@PathVariable("id") Long id, @RequestBody @Valid UserPasswordUpdateDTO passwordUpdateDTO) {
        UserResponseDTO updatedUser = service.updatePassword(id, passwordUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }
}
