package library.backend.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String phoneNo;
    private String password;

    public Student() {
    }

    public Student(String email, String phoneNo, String password) {
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    // Getter for studentId
    public Long getId() {
        return id;
    }

    // Setter for studentId
    public void setId(Long studentId) {
        this.id = studentId;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for phoneNo
    public String getPhoneNo() {
        return phoneNo;
    }

    // Setter for phoneNo
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }
}
