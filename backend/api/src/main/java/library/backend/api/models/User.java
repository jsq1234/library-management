package library.backend.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String phoneNo;
    private String password;
    private String role;

    public User(Long id, String email, String phoneNo, String password, String role, List<Issue> issues) {
        this.id = id;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.role = role;
        this.issues = issues;
    }

    @OneToMany(mappedBy = "user")
    private List<Issue> issues;

    public User() {
    }

    public User(String email, String phoneNo, String password) {
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Issue> getIssues() {
        return this.issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

}
