package library.backend.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import library.backend.api.dto.SignUpRequestDto;
import library.backend.api.exceptions.UserAlreadyExistsException;
import library.backend.api.models.User;
import library.backend.api.repositories.UserRepository;
import library.backend.api.services.AuthService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.authService = new AuthService(authenticationManager, passwordEncoder, userRepository);
    }

    @Test
    @DisplayName("SignUpWithExistingUserThrowsException: Signing up with using existing credentials throws error.")
    public void SignUpWithExistingUserThrowsException() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto("manan", "test@example.com", "8178610509", "password");
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> authService.signUp(signUpRequestDto));
    }

    @Test
    @DisplayName("SignUp_SucessfullySavesUser: Signing Up sucessfully saves user to the database")
    public void SignUp_SucessfullySavesUser() {
        SignUpRequestDto dto = new SignUpRequestDto("manan", "test@example.com", "8178610509", "password");
        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(passwordEncoder.encode(dto.password())).thenReturn("encodedPassword");

        authService.signUp(dto);

        var user = new User();
        user.setEmail(dto.email());
        user.setPassword("encodedPassword");
        user.setPhoneNo(dto.phoneNo());
        user.setRole("USER");

        verify(userRepository).save(user);
    }
}
