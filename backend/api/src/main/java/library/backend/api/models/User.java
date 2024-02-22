package library.backend.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.*;

import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.authorization.AuthoritiesAuthorizationManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits")
    private String phoneNo;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    @NotBlank(message = "Role is required")
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Issue> issues;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var roleMapper = new SimpleAuthorityMapper();
        roleMapper.setConvertToUpperCase(true);
        return roleMapper.mapAuthorities(List.of(new SimpleGrantedAuthority(this.role)));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
