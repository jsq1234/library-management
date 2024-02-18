package library.backend.api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import library.backend.api.dto.SignUpRequestDto;
import library.backend.api.repositories.UserRepository;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authManager, PasswordEncoder passwordEncoder,
            UserRepository userRepository) {
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public String login(String email, String password) {
        return null;
    }

    public String signUp(SignUpRequestDto signUpRequestDto) {
        return null;
    }
}
