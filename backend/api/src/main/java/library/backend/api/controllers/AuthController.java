package library.backend.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.backend.api.dto.AuthResponseDto;
import library.backend.api.dto.LoginRequestDto;
import library.backend.api.dto.SignUpRequestDto;
import library.backend.api.services.AuthService;
import library.backend.api.utils.AuthStatus;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        var jwtToken = authService.login(loginRequestDto);
        var authResponseDto = new AuthResponseDto(jwtToken, AuthStatus.LOGIN_SUCCESS);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authResponseDto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        var jwtToken = authService.signUp(signUpRequestDto);
        var authResponseDto = new AuthResponseDto(jwtToken, AuthStatus.REGISTER_SUCCESS);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authResponseDto);
    }

}
