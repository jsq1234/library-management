package library.backend.api.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import library.backend.api.dto.LoginRequestDto;
import library.backend.api.dto.SignUpRequestDto;
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

    public String login(LoginRequestDto loginRequest) throws MissingLoginFieldsException {
        if (loginRequest.password() == null || (loginRequest.email() == null && loginRequest.phoneNo() == null)) {
            throw new MissingLoginFieldsException("Incorrect login message format");
        }

        if (loginRequest.email() != null) {
            return loginAndGenerateJwtToken(loginRequest.email(), loginRequest.password());
        } else {
            return loginAndGenerateJwtToken(loginRequest.phoneNo(), loginRequest.password());
        }

    }

    private String loginAndGenerateJwtToken(String username, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(username, password);
        var authenticate = authManager.authenticate(authToken);
        return JwtUtils.generateToken(((UserDetails) (authenticate.getPrincipal())).getUsername());
    }

    public String signUp(SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(signUpRequestDto.email())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        var encodedPassword = passwordEncoder.encode(signUpRequestDto.password());

        var user = new User();
        user.setEmail(signUpRequestDto.email());
        user.setPassword(encodedPassword);
        user.setPhoneNo(signUpRequestDto.phoneNo());
        user.setRole("USER");

        userRepository.save(user);

        return JwtUtils.generateToken(user.getEmail());
    }
}
