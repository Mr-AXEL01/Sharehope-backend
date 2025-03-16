package net.axel.sharehope.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.axel.sharehope.security.domain.dto.user.AuthenticationResponseDTO;
import net.axel.sharehope.security.domain.dto.user.UserLoginDTO;
import net.axel.sharehope.security.domain.dto.user.UserRegisterDTO;
import net.axel.sharehope.security.domain.dto.user.UserResponseDTO;
import net.axel.sharehope.security.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AuthController.CONTROLLER_PATH)

@RequiredArgsConstructor
public class AuthController {

    public final static String CONTROLLER_PATH = "api/v1/auth";

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@ModelAttribute @Valid UserRegisterDTO requestDTO) {
        UserResponseDTO newUser = service.register(requestDTO);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        var user = service.login(loginDTO);
        return ResponseEntity.ok(user);
    }

}
