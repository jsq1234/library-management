package library.backend.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import library.backend.api.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
