package library.backend.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;

@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private Integer issuePeriod;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    public Issue() {
    }

    // Explicit constructor
    public Issue(LocalDate issueDate, LocalDate returnDate, Integer issuePeriod, Student student) {
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.issuePeriod = issuePeriod;
        this.student = student;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getIssuePeriod() {
        return issuePeriod;
    }

    public void setIssuePeriod(Integer issuePeriod) {
        this.issuePeriod = issuePeriod;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
