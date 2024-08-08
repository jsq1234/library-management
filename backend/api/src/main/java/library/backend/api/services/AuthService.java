package library.backend.api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import library.backend.api.utils.Status;
import library.backend.api.dto.AuthResponse;
import library.backend.api.dto.LoginRequest;
import library.backend.api.dto.SignUpRequest;
import library.backend.api.exceptions.MissingLoginFieldsException;
import library.backend.api.exceptions.UserAlreadyExistsException;
import library.backend.api.models.User;
import library.backend.api.repositories.UserRepository;
import library.backend.api.utils.JwtUtils;

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

    public AuthResponse login(LoginRequest loginRequest) throws MissingLoginFieldsException {
        if (loginRequest.password() == null || (loginRequest.email() == null && loginRequest.phoneNo() == null)) {
            throw new MissingLoginFieldsException("Incorrect login message format");
        }

        if (loginRequest.email() != null) {
            return loginAndGenerateJwtToken(loginRequest.email(), loginRequest.password());
        } else {
            return loginAndGenerateJwtToken(loginRequest.phoneNo(), loginRequest.password());
        }

    }

    private AuthResponse loginAndGenerateJwtToken(String username, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(username, password);
        var authenticate = authManager.authenticate(authToken);
        var authenticatedUser = (User) authenticate.getPrincipal();
        String jwtToken = JwtUtils.generateToken(authenticatedUser.getUsername());
        return AuthResponse.builder()
                .email(authenticatedUser.getEmail())
                .name(authenticatedUser.getName())
                .phoneNo(authenticatedUser.getPhoneNo())
                .token(jwtToken)
                .role(authenticatedUser.getRole())
                .status(Status.LOGIN_SUCCESS)
                .build();
    }

    public AuthResponse signUp(SignUpRequest signUpRequestDto) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(signUpRequestDto.email())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        var encodedPassword = passwordEncoder.encode(signUpRequestDto.password());

        var user = User.builder()
                .email(signUpRequestDto.email())
                .password(encodedPassword)
                .phoneNo(signUpRequestDto.phoneNo())
                .name(signUpRequestDto.name())
                .role("USER")
                .build();

        userRepository.save(user);

        String jwtToken = JwtUtils.generateToken(user.getEmail());

        return AuthResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .phoneNo(user.getPhoneNo())
                .token(jwtToken)
                .role(user.getRole())
                .status(Status.REGISTER_SUCCESS)
                .build();
    }
}
