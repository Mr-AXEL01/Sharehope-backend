package net.axel.sharehope.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.axel.sharehope.security.domain.dto.user.UserRegisterDTO;
import net.axel.sharehope.security.domain.dto.user.UserResponseDTO;
import net.axel.sharehope.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserController.CONTROLLER_PATH)

@RequiredArgsConstructor
public class UserController {

    public final static String CONTROLLER_PATH = "api/v1";

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@ModelAttribute @Valid UserRegisterDTO requestDTO) {
        UserResponseDTO newUser = service.register(requestDTO);
        return ResponseEntity.ok(newUser);
    }

}
